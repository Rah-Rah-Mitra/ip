# AI-Assisted iP Development Log

This document chronicles the use of AI tools in the development of this project.

## Tooling: VS Code MCP Toolkit with Gemini

The primary development environment utilizes the **VS Code MCP (Multi-platform Coding Platform) toolkit**, which integrates Google's Gemini model as an interactive coding agent. This setup allows for a conversational approach to software development directly within the IDE.

### Gemini Coding Agent Functionality

The Gemini agent provides a suite of capabilities to accelerate development, including:

*   **Code Generation and Modification:** Writing new code from scratch, refactoring existing code, and fixing bugs based on natural language prompts.
*   **Codebase Comprehension:** Reading and analyzing the entire project to understand existing conventions, structures, and logic.
*   **File System Operations:** Creating, deleting, and modifying files and directories.
*   **Shell Command Execution:** Running build scripts, executing tests, and interacting with version control (Git) to manage the project lifecycle.
*   **Search and Discovery:** Finding relevant code snippets, files, or configurations across the workspace.

This integrated approach aims to minimize manual coding by leveraging AI to handle tasks ranging from simple boilerplate to complex logic implementation, allowing the developer to focus on high-level design and problem-solving.

---

## Week 2

In Week 2, AI was heavily utilized to build the core application logic, covering features from Level 1 to Level 6. I assisted by generating the initial class hierarchy for tasks (`Task`, `Todo`, `Event`, `Deadline`) and implementing the primary user commands for adding, listing, marking, and deleting tasks. Additionally, I helped create the initial CI workflow file (`.github/workflows/ci.yml`) and configured it to run tests using a manual setup, establishing a baseline for automated testing before the introduction of a formal build tool.

## Week 3

Following the merge of the Gradle support branch in Week 3, the focus shifted to updating the build and test process. I assisted in transitioning the CI workflow to use Gradle. This involved modifying the `.github/workflows/ci.yml` file to replace the previous manual test execution commands with the appropriate Gradle tasks (e.g., `./gradlew build`). This change aligned the project's automated testing environment with its new Gradle-based build system.

## Week 4

In Week 4, the application's command-line interface was replaced with a graphical user interface using JavaFX. I assisted in this transition by focusing on the UI's visual appeal. I generated the CSS stylesheets to define the look and feel of the application, creating rules for the main window layout and the dialog boxes used for conversations. This included styling for colors, fonts, and spacing. Furthermore, I helped link these stylesheets to the FXML layout files and apply specific CSS classes to elements, such as the bot's replies, to visually distinguish them from user inputs.

## Week 5

*Observations and AI usage for this week...*

## Week 6

*Observations and AI usage for this week...*