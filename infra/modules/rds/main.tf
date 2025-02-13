resource "aws_security_group" "rds_sg" {
  name        = "rds-sg-tf"
  description = "Permite acceso MySQL"
  vpc_id      = var.vpc_id

  ingress {
    from_port   = 3306
    to_port     = 3306
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
}

resource "aws_db_instance" "rds_mysql" {
  identifier              = "franchise-tf"
  engine                  = "mysql"
  engine_version          = "8.0.39"
  instance_class          = "db.t4g.micro"
  allocated_storage       = 20
  publicly_accessible     = true
  vpc_security_group_ids  = [aws_security_group.rds_sg.id]
  username                = "admin"
  password                = var.db_password
  db_name                 = "franchise"
  multi_az                = false
  storage_type            = "gp2"
  backup_retention_period = 7
  skip_final_snapshot     = true
}
