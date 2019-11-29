# Mongo Script Versioning (MSV)
Mongo script versioning is a maven plugin. It helps you to migrate your .js and .json file into the Mongo database. **It is avaiable in maven central!**
   
**Usage:**
```
  <plugins>
            <plugin>
                <groupId>com.github.bex1111</groupId>
                <artifactId>msv-maven-plugin</artifactId>
                <version>0.3.1-SNAPSHOT</version>
                <configuration>
                    <dbName>MSV</dbName>
                    <dbAddress>localhost</dbAddress>
                    <dbPort>27017</dbPort>
                    <fileLocation>./src/main/resources/msv</fileLocation>
                    <outputLocation>../tocsv</outputLocation>
                    <revertVersion>${revertVersion}</revertVersion>
                </configuration>
            </plugin>
        </plugins>
```

You can find a demo project under the test_MSV folder.
**If you have question fell free to ask me.** 
   
## Migrate
### Command:
``` mvn msv:migrate ```

### About

Migrate command migrate your file from "fileLocation" in to the mongo database. You have to follow rules when create a file. Js file name have to be like version_name.js where version specify the order. Version only contain numeric and alphabetic character. Description helps you to remember what is in the file. Json file contains one more field. It is a collection name. So json file name have to follow this convection version_description_collection.json.

If you already migrate a file you can't change it. Otherwise you can't migrate new file. This methodology grant, that program can version controlling the files.

**File name example:**
- 0001_test.js
- 0002_test_cars.json

## Validate

### Command:
``` mvn msv:validate ```

### About

Validate command check your file names and etc. Important, if validate run success it doesn't guaranteed that migrate will also be success.  You have to follow rules when create a file. Js file name have to be like version_name.js where version specify the order. Version only contain numeric and alphabetic character. Description helps you to remember what is in the file. Json file contains one more field. It is a collection name. So json file name have to follow this convection version_description_collection.json.

If you already migrate a file you can't change it. Otherwise you can't validate or migrate new file. This methodology grant, that program can version controlling the files. Validate check this conception too.

**File name example:**
- 0001_test.js
- 0002_test_cars.json

## Revert
### Command:
``` mvn msv:revert ```

**If you follow example you can specify version in command line:**

``` mvn msv:revert -DrevertVersion=0001 ```

### About

If your database goes inconsistent state. You can revert changes with msv in msv collection, not in entire database. It means that you can migrate file again. But MSV can't modify your database. So you should revert changes in your collection manually. 

If you doesn't specify any version, MSV revert all version. If you do, your msv collection goes to that version.

## ToCsv

### Command:
``` mvn msv:tocsv ```

### About

This command can generate document for you, which contain every usefull information about migration history.
This document type is csv, so you can easily open it with Excel.

Document contains this fields:
- full_name (File name)
- description (File second tag)
- version (File first tag)
- installed_by (Migrate command executor username )
- date (Migration date)
- type (File type)
- checksum (Check sum from file)
- collection_name (Json file third tag)

## Listing

### Command:
``` mvn msv:listing ```

### About

This command help you if you wanna debug something or getting info what is in the database.
 
