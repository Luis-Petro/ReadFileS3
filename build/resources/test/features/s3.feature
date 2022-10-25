Feature: get the log
  Background:
    * def bucketName = "nu0286001-central-channel-repository-qa-daily-raw-date"
    * def aws = Java.type('AWS.AwsS3ReadFiles')

  @ValidateLastRegister
  Scenario: Validate last register
    Given def result = aws.readFileInS3(bucketName)
    When print result
    Then match result.transactionId != ""