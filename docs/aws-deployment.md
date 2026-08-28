# AWS deployment guide

1. Create a VPC across two Availability Zones: public subnets for the ALB and private subnets for ECS tasks and RDS. Permit internet traffic only to the ALB on HTTPS; allow the ALB security group to reach the gateway and task security group; allow only service tasks to reach RDS on 5432.
2. Create one RDS PostgreSQL database (or database instance) per stateful service. Store JDBC credentials in Secrets Manager, encrypt RDS storage, enable backups and Multi-AZ in production.
3. Create ECR repositories per service. Give ECS task roles read access only to the required Secrets Manager entries, S3 prefixes, and CloudWatch log groups.
4. Create ECS Fargate task definitions with container health checks at `/actuator/health`. Run Config and Eureka privately; place only the API Gateway target group behind the ALB.
5. Create ALB listener rules to forward all public API paths to the gateway. Configure ECS target tracking autoscaling on CPU and memory utilization.
6. Enable CloudWatch Container Insights, structured application logs, alarms for 5xx count/latency/RDS capacity, and an S3 lifecycle policy for retained logs or exports.

Never commit AWS credentials, database passwords, JWT signing keys, or payment-provider keys. Supply them at runtime through Secrets Manager and IAM roles.
