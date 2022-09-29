Feature: get the log
  Background:
    * def urlbase = "https://s3.console.aws.amazon.com/s3/buckets?region=us-east-1"
    * def fileName = "PruebaUno.log"
    * def bucket = "bucketpruebas15"
    * def aws = Java.type('AWS.AwsConnect')

  @IfExist
  Scenario: I want to list the files
    * configure ssl = true
    Given def bol = aws.checkIfFileExists(bucket, fileName)
    When print bol
    Then match bol == true

  @ListS3
  Scenario: I want to list all files
    Given def bol = aws.filesInS3(bucket)
    When print bol
    Then match bol contains fileName

  @DownloadFileS3
  Scenario: I want to list get a fill
    Given json resjson =  aws.downloadFileS3(bucket,fileName)
    When print resjson
    When print resjson.transactionid
    Then match resjson.transactionid == "f3e288f0-346e-11ed-49b3-458742914e94"

