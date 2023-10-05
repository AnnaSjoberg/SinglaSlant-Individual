# SinglaSlant-GitHub-Actions
GitHub Actions school project

# Terminal commands for testing:
- Unit tests: ./gradlew clean unitTest
- Integration tests: ./gradlew clean integrationTest
- API tests: ./gradlew clean apiTest
- All tests: ./gradlew clean test

# Generating a JaCoCo test report:
- Terminal command: ./gradlew test jacocotestreport
- Report is generated at build/jacocoHtml/index.html
- Open report in browser

# Terminal commands for Checkstyle:
- Checking main code: ./gradlew checkstyleMain
- Checking tests: ./gradlew checkstyleTest
