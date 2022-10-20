Feature: get the log
  Background:
    * def bucket = "nu0286001-central-channel-repository-qa-daily-raw-data"
    * def aws = Java.type('AWS.AwsConnect')



  @ValidateLastRegister
  Scenario: Validate last register
    Given def result = aws.readFileS3(bucket)
    When print result
    Then match result.transactionId contains "d26304c5-f2e3-71a1-3c75-0279b432aff1"