resource "aws_api_gateway_rest_api" "api" {
  name = "SimpleApi"
}

#############################
# Endpoint: /branch
#############################
resource "aws_api_gateway_resource" "branch" {
  rest_api_id = aws_api_gateway_rest_api.api.id
  parent_id   = aws_api_gateway_rest_api.api.root_resource_id
  path_part   = "branch"
}

resource "aws_api_gateway_method" "branch_method" {
  rest_api_id   = aws_api_gateway_rest_api.api.id
  resource_id   = aws_api_gateway_resource.branch.id
  http_method   = "ANY"
  authorization = "NONE"
}

resource "aws_api_gateway_integration" "branch_integration" {
  rest_api_id             = aws_api_gateway_rest_api.api.id
  resource_id             = aws_api_gateway_resource.branch.id
  http_method             = aws_api_gateway_method.branch_method.http_method
  integration_http_method = "ANY"
  type                    = "HTTP_PROXY"
  uri                     = "http://${var.alb_dns_name}/branch"
  passthrough_behavior    = "WHEN_NO_MATCH"
}

#############################
# Endpoint: /franchise
#############################
resource "aws_api_gateway_resource" "franchise" {
  rest_api_id = aws_api_gateway_rest_api.api.id
  parent_id   = aws_api_gateway_rest_api.api.root_resource_id
  path_part   = "franchise"
}

resource "aws_api_gateway_method" "franchise_method" {
  rest_api_id   = aws_api_gateway_rest_api.api.id
  resource_id   = aws_api_gateway_resource.franchise.id
  http_method   = "ANY"
  authorization = "NONE"
}

resource "aws_api_gateway_integration" "franchise_integration" {
  rest_api_id             = aws_api_gateway_rest_api.api.id
  resource_id             = aws_api_gateway_resource.franchise.id
  http_method             = aws_api_gateway_method.franchise_method.http_method
  integration_http_method = "ANY"
  type                    = "HTTP_PROXY"
  uri                     = "http://${var.alb_dns_name}/franchise"
  passthrough_behavior    = "WHEN_NO_MATCH"
}

#############################
# Endpoint: /product
#############################
resource "aws_api_gateway_resource" "product" {
  rest_api_id = aws_api_gateway_rest_api.api.id
  parent_id   = aws_api_gateway_rest_api.api.root_resource_id
  path_part   = "product"
}

resource "aws_api_gateway_method" "product_method" {
  rest_api_id   = aws_api_gateway_rest_api.api.id
  resource_id   = aws_api_gateway_resource.product.id
  http_method   = "ANY"
  authorization = "NONE"
}

resource "aws_api_gateway_integration" "product_integration" {
  rest_api_id             = aws_api_gateway_rest_api.api.id
  resource_id             = aws_api_gateway_resource.product.id
  http_method             = aws_api_gateway_method.product_method.http_method
  integration_http_method = "ANY"
  type                    = "HTTP_PROXY"
  uri                     = "http://${var.alb_dns_name}/product"
  passthrough_behavior    = "WHEN_NO_MATCH"
}

#############################
# Despliegue del API Gateway
#############################
resource "aws_api_gateway_deployment" "api_deployment" {
  depends_on = [
    aws_api_gateway_integration.branch_integration,
    aws_api_gateway_integration.franchise_integration,
    aws_api_gateway_integration.product_integration
  ]
  rest_api_id = aws_api_gateway_rest_api.api.id
  stage_name  = "prod"
}
