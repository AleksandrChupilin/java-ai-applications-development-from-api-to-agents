---
name: ums-user-management
description: >
  TODO: Write a concise description explaining the skill's purpose — managing users in the UMS
  via CRUD operations, search, web enrichment; and when to activate it
license: Apache-2.0
metadata:
  author: ai-powered-apps-development-expert
  version: "2.0"
---

# UMS User Management

<!-- TODO: Describe the agent's role and name the two MCP servers it has access to -->

---

## MCP Server Connections

<!-- TODO:
     Add a Markdown table with three columns: Server | Transport | URL
     - UMS MCP Server      → streamable-http, http://localhost:8005/mcp
     - DuckDuckGo Search   → streamable-http, http://localhost:8000/mcp -->

---

## Available MCP Tools

### UMS MCP Server Tools

<!-- TODO:
     Add a table listing all five UMS tools with their description and key parameters:
       get_user_by_id, search_user, add_user, update_user, delete_user
     Below the table document:
       - UserCreate required fields (name, surname, email, about_me)
       - UserCreate optional fields (phone, date_of_birth, address, gender, company, salary, credit_card)
       - UserSearchRequest fields (all optional, partial case-insensitive match except gender)
       - UserUpdate fields (same optional set as UserCreate) -->

---

### DuckDuckGo Search MCP Server Tools

<!-- TODO:
     Add a table listing both DuckDuckGo tools with parameters:
       - search: query, max_results (default 10, max 50)
       - fetch_content: url (must start with http:// or https://)
     Describe when to use search vs fetch_content -->

---

## Operating Rules

<!-- TODO:
     Number the operating rules:
     1. Always explain actions before executing any tool call
     2. UMS-first: always query UMS before resorting to web search
     3. Web search for enrichment when user data is incomplete or ambiguous
     4. Confirm before creating: present the full proposed profile and wait for confirmation
     5. Deletions require confirmation: warn that the action is permanent and irreversible
     6. Format responses clearly and structurally
     7. Handle errors gracefully: explain what went wrong and suggest alternatives -->

---

## Workflows

### Finding a User

<!-- TODO:
     Step-by-step workflow:
     1. Call search_user with available criteria
     2. If results found → present them to the operator
     3. If no results → inform the operator; offer web search if context suggests a real person -->

### Adding a User

<!-- TODO:
     Step-by-step workflow:
     1. Collect available user data from the operator
     2. Identify missing required fields (name, surname, email, about_me)
     3. If data incomplete:
        a. Call search (DuckDuckGo) with the person's name/company/context
        b. Optionally call fetch_content on a relevant URL for deeper detail
        c. Build a complete UserCreate profile from gathered data
        d. Present the full profile to the operator for confirmation
     4. On confirmation → call add_user -->

### Updating a User

<!-- TODO:
     Step-by-step workflow:
     1. If user_id unknown → call search_user to locate the user first
     2. Confirm which fields to update with the operator
     3. Call update_user with only the fields that need to change
     4. Report success or explain any error -->

### Deleting a User

<!-- TODO:
     Step-by-step workflow:
     1. If user_id unknown → call search_user to locate the user first
     2. Display user details and warn: deletion is permanent and cannot be undone
     3. Wait for explicit operator confirmation
     4. On confirmation → call delete_user
     5. Report success or explain any error -->

---

## Boundaries

<!-- TODO: Define the agent's scope — user management only; politely redirect off-topic requests -->
