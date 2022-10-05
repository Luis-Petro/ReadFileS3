Feature: get the log
  Background:
    * def urlbase = "https://s3.console.aws.amazon.com/s3/buckets?region=us-east-1"
    * def fileName = "year=2022"
    * def bucket = "nu0286001-central-channel-repository-qa-daily-raw-date"
    * def aws = Java.type('AWS.AwsConnect')

  @IfExist
  Scenario: I want to list the files
    * configure ssl = true
    Given def bol = aws.checkIfFileExists(bucket, fileName)
    When print bol
    Then match bol == true

  @ListS3
  Scenario: I want to list all files
    Given def bol = aws.ShowContent(bucket)
    When print bol
    Then match bol contains fileName

  @Validate
  Scenario Outline: Validate field in file S3
    Given json resJson =  aws.readFileS3(bucket,fileName)
    When  print resJson
    Then  match resJson.documentType   == "<documenttype>"
    And   match resJson.documentNumber == "<documentnumber>"

    Examples:
      |   documenttype   | documentnumber   |
      |    CC            |   202203282    |


