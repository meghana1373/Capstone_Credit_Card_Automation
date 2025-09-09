Capstone Credit Card Automation - Skeleton

What is included:
- Maven project skeleton
- Cucumber feature files (positive & negative) with 3 scenarios each
- DriverFactory, ConfigReader, Screenshot utility and basic Page object
- TestNG runner wired to Cucumber

Notes:
- Test steps are skeletons; fill POM locators and step implementations to match the site flow.
- No credentials are hardcoded for persistent use; example values appear in step skeletons as placeholders.
- Screenshots are captured after each scenario and saved into output/screenshots and attached to Cucumber scenario.

To run:
- mvn clean test (or run testng.xml)
