# AI-Assisted iP Development Log

This document chronicles my use of AI coding agents in the development of this project. The log is written from my perspective as the developer, detailing how I leveraged these tools to accelerate and enhance my workflow.

## AI Tooling

Throughout this project, I primarily utilized two AI coding agents:

1.  **Gemini (via VS Code MCP Toolkit):** My main development environment included the VS Code MCP (Multi-platform Coding Platform) toolkit, which integrates Google's Gemini model. This tool provides an interactive, conversational interface within the IDE, allowing me to prompt the agent to perform complex tasks like refactoring, code generation, and project-wide analysis. Its capabilities include:
    *   **Code Generation and Modification:** Writing new code, refactoring existing code, and fixing bugs based on natural language.
    *   **Codebase Comprehension:** Reading and analyzing the project to understand conventions and logic.
    *   **File System Operations:** Creating, deleting, and modifying files.
    *   **Shell Command Execution:** Running builds, tests, and Git commands.

2.  **GitHub Copilot:** I also used GitHub Copilot for its powerful code completion and generation features. Copilot excels at generating code snippets, suggesting implementations, and creating comprehensive tests. I leveraged its capabilities to work on tasks in parallel by having it generate code in separate branches, which I could then review and merge.

---

## Week 2

In Week 2, I heavily utilized the Gemini coding agent to build the core application logic, covering features from Level 1 to Level 6. I directed the agent to generate the initial class hierarchy for tasks (`Task`, `Todo`, `Event`, `Deadline`) and implement the primary user commands for adding, listing, marking, and deleting tasks. Additionally, I used it to create the initial CI workflow file (`.github/workflows/ci.yml`) and configure it to run tests with a manual setup, establishing a baseline for automated testing.

## Week 3

Following the merge of the Gradle support branch in Week 3, I focused on updating the build and test process. I used the Gemini agent to transition the CI workflow to use Gradle. This involved instructing it to modify the `.github/workflows/ci.yml` file, replacing the manual test execution commands with the appropriate Gradle tasks (e.g., `./gradlew build`), aligning the CI environment with the new build system.

## Week 4

In Week 4, I replaced the application's command-line interface with a graphical user interface using JavaFX. I leveraged the Gemini agent to handle the UI's visual styling. I prompted it to generate the CSS stylesheets that define the application's look and feel, including rules for the main window and dialog boxes. It also assisted in linking these stylesheets to the FXML layout files and applying specific CSS classes to elements to distinguish bot replies from user inputs.

## Week 5

For the B-CD/Extension increment in Week 5, I used the Gemini agent for two key tasks: improving the object-oriented design and adding a new trivia feature.

First, I initiated a major refactoring effort by having the agent identify duplicated logic in the `DeleteCommand`, `MarkCommand`, and `UnmarkCommand` classes. Based on its analysis, I had it abstract the shared logic into new base classes (`MultiTaskCommand` and `UpdateStatusCommand`), which reduced code redundancy. I also used it to modernize the `Parser` by converting its logic to use Java Streams.

Second, I accelerated the development of the new trivia feature by instructing the agent to replicate the existing command design pattern. It analyzed the structure of commands like `ListCommand` and generated a parallel set of classes for the trivia functionality (`TriviaCommand`, `TriviaListCommand`, etc.), ensuring the new feature integrated consistently with the established architecture.

## Week 6

In Week 6, I explored using GitHub Copilot and Codespaces to enhance the project's testing capabilities. I used the Copilot coding agent to generate comprehensive tests for the application, which it delivered as a series of pull requests that I then reviewed and merged. The testing was rigorous and significantly improved code quality. The agent's ability to work on a separate branch allowed me to continue my own development in parallel. Furthermore, Codespaces provided a cloud-based Linux environment, which allowed me to test the application's cross-platform compatibility and ensure it worked correctly on a different operating system. The overall experience with both Copilot and Codespaces was highly positive.