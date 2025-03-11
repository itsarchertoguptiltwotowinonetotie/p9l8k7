# Problem Statement

## Title: Fantasy Cricket Scorecard Application

### Introduction
Cricket is one of the most popular sports globally, with millions of passionate fans. The rise of fantasy sports has provided an engaging platform for cricket enthusiasts to create their fantasy teams, compete with friends, and test their knowledge of the game. However, there is a growing demand for a dedicated fantasy cricket application that not only allows users to create and manage their teams but also provides real-time, ball-by-ball updates of live cricket matches.

### Problem Description
Currently, cricket enthusiasts who participate in fantasy leagues have to rely on multiple platforms to manage their teams and stay updated with live scores. This fragmentation leads to a disjointed user experience, where users have to switch between different applications for team management, league participation, and live score updates. This lack of integration results in inefficiencies and reduces the overall enjoyment of the fantasy cricket experience.

### Objective
The objective of this project is to develop a comprehensive Fantasy Cricket Scorecard Application that seamlessly integrates fantasy team management, league participation, and real-time ball-by-ball score updates. The application aims to provide a unified platform for cricket enthusiasts, enhancing their engagement and satisfaction.

### Goals
1. **User-Friendly Registration and Authentication**: Enable users to easily register and log in to the application using email, social media accounts, or phone numbers.
2. **Team Creation and Management**: Allow users to create and manage their fantasy cricket teams by selecting players from a pool.
3. **League Creation and Participation**: Facilitate the creation of public and private leagues, allowing users to join and compete with friends and other cricket fans.
4. **Real-Time Score Updates**: Provide ball-by-ball live score updates, notifications, and commentary to keep users informed about ongoing matches.
5. **User Notifications and Alerts**: Send personalized notifications and alerts to users about upcoming matches, team performance, and league standings.
6. **Global Leaderboard and Rankings**: Display a leaderboard showcasing top users based on their team performance, both globally and within individual leagues.

### Target Audience
The primary target audience for this application includes:
- Cricket enthusiasts and fans who enjoy following live matches.
- Fantasy sports players who participate in fantasy cricket leagues.
- Individuals looking for a comprehensive platform to manage their fantasy teams and stay updated with live scores.

### Challenges
1. **Real-Time Data Integration**: Ensuring real-time, accurate, and reliable updates of live cricket scores and statistics.
2. **Scalability**: Handling high traffic volumes during major cricket events and tournaments.
3. **User Engagement**: Keeping users engaged with personalized notifications, interactive features, and an intuitive user interface.
4. **Data Privacy and Security**: Ensuring the security and privacy of user data, including personal information and team details.

### Conclusion
The Fantasy Cricket Scorecard Application aims to address the existing fragmentation in the fantasy cricket experience by providing a unified platform for team management, league participation, and live score updates. By achieving these goals, the application will enhance user engagement, satisfaction, and overall enjoyment of the fantasy cricket experience.


------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

 Software Requirements Specification (SRS)

 Table of Contents

- Table of Contents(table-of-contents)
- List of Figures(list-of-figures)
- 1.0. Introduction(10-introduction)
  - 1.1. Purpose(11-purpose)
  - 1.2. Scope of Project(12-scope-of-project)
  - 1.3. Glossary(13-glossary)
  - 1.4. References(14-references)
  - 1.5. Overview of Document(15-overview-of-document)
- 2.0. Overall Description(20-overall-description)
  - 2.1. System Environment(21-system-environment)
  - 2.2. Functional Requirements Specification(22-functional-requirements-specification)
    - 2.2.1. User Use Cases(221-user-use-cases)
      - Use Case: Register User(use-case-register-user)
      - Use Case: Create Team(use-case-create-team)
      - Use Case: Join League(use-case-join-league)
      - Use Case: View Live Scores(use-case-view-live-scores)
      - Use Case: Receive Notifications(use-case-receive-notifications)
    - 2.2.2. Admin Use Cases(222-admin-use-cases)
      - Use Case: Manage Users(use-case-manage-users)
      - Use Case: Manage Leagues(use-case-manage-leagues)
      - Use Case: Update Scores(use-case-update-scores)
  - 2.3. User Characteristics(23-user-characteristics)
  - 2.4. Non-Functional Requirements(24-non-functional-requirements)
- 3.0. Requirements Specification(30-requirements-specification)
  - 3.1. External Interface Requirements(31-external-interface-requirements)
  - 3.2. Functional Requirements(32-functional-requirements)
    - 3.2.1. Register User(321-register-user)
    - 3.2.2. Create Team(322-create-team)
    - 3.2.3. Join League(323-join-league)
    - 3.2.4. View Live Scores(324-view-live-scores)
    - 3.2.5. Receive Notifications(325-receive-notifications)
    - 3.2.6. Manage Users(326-manage-users)
    - 3.2.7. Manage Leagues(327-manage-leagues)
    - 3.2.8. Update Scores(328-update-scores)
  - 3.3. Detailed Non-Functional Requirements(33-detailed-non-functional-requirements)
    - 3.3.1. Logical Structure of the Data(331-logical-structure-of-the-data)
    - 3.3.2. Security(332-security)
- Index(index)

 List of Figures

- Figure 1 - System Environment
- Figure 2 - User Registration Process
- Figure 3 - Admin Use Cases
- Figure 4 - Logical Structure of the Fantasy Cricket Data

 1.0. Introduction

 1.1. Purpose
The purpose of this document is to present a detailed description of the Fantasy Cricket Scorecard Application. It will explain the purpose and features of the system, the interfaces, what the system will do, the constraints under which it must operate, and how the system will react to external stimuli. This document is intended for both the stakeholders and the developers of the system.

 1.2. Scope of Project
This software system will be a Fantasy Cricket Scorecard Application that allows users to create and manage their fantasy cricket teams, participate in various leagues, and receive ball-by-ball score updates. The system will provide tools to assist users in managing their teams, viewing live scores, and receiving notifications about the performance of their teams and players. The application will also include an admin interface for managing users, leagues, and live scores.

 1.3. Glossary
- Fantasy Cricket: A game where users create virtual teams of real cricket players and score points based on the players' performance in actual matches.
- User: A person who uses the application to create and manage fantasy cricket teams.
- Admin: A person who manages the application, users, leagues, and live scores.
- League: A group of users competing against each other based on their fantasy cricket teams.
- Live Scores: Real-time updates of cricket match scores.

 1.4. References
- IEEE. IEEE Std 830-1998 IEEE Recommended Practice for Software Requirements Specifications. IEEE Computer Society, 1998.
- Cricket API Documentation: Cricket API(https://www.cricketapi.com/)
- Fantasy Sports Guidelines: Fantasy Sports Guidelines(https://www.fantasysportsguide.com/)

 1.5. Overview of Document
The next chapter, the Overall Description section, gives an overview of the functionality of the product. It describes the informal requirements and is used to establish a context for the technical requirements specification in the next chapter.
The third chapter, the Requirements Specification section, is written primarily for the developers and describes in technical terms the details of the functionality of the product. Both sections describe the same software product in its entirety but are intended for different audiences and thus use different language.

 2.0. Overall Description

 2.1. System Environment

!System Environment()

The Fantasy Cricket Scorecard Application has two main components: the User Interface (UI) and the Admin Interface. The UI allows users to register, create and manage teams, join leagues, and view live scores. The Admin Interface allows admins to manage users, leagues, and live scores. Both components interact with a central server that stores all the data and communicates with external APIs for live cricket scores.

 2.2. Functional Requirements Specification
This section outlines the use cases for both users and admins.

 2.2.1. User Use Cases

 Use Case: Register User
Brief Description: The user registers an account on the application.

Initial Step-By-Step Description:
1. The user accesses the registration page.
2. The user fills in the registration form with their details.
3. The system validates the information and creates a new account.
4. The system sends a confirmation email to the user.

!User Registration Process()

 Use Case: Create Team
Brief Description: The user creates a fantasy cricket team.

Initial Step-By-Step Description:
1. The user logs into their account.
2. The user navigates to the team creation page.
3. The user selects players from a pool to form their team.
4. The system saves the team information.

 Use Case: Join League
Brief Description: The user joins a league to compete with other users.

Initial Step-By-Step Description:
1. The user logs into their account.
2. The user navigates to the league page.
3. The user selects a league to join.
4. The system adds the user to the league.

 Use Case: View Live Scores
Brief Description: The user views live cricket scores.

Initial Step-By-Step Description:
1. The user logs into their account.
2. The user navigates to the live scores page.
3. The system displays live scores and updates them in real-time.

 Use Case: Receive Notifications
Brief Description: The user receives notifications about their team’s performance.

Initial Step-By-Step Description:
1. The user logs into their account.
2. The user sets notification preferences.
3. The system sends notifications based on the user’s preferences.

 2.2.2. Admin Use Cases

 Use Case: Manage Users
Brief Description: The admin manages user accounts.

Initial Step-By-Step Description:
1. The admin logs into the admin interface.
2. The admin navigates to the user management page.
3. The admin can add, update, or remove user accounts.

 Use Case: Manage Leagues
Brief Description: The admin manages leagues.

Initial Step-By-Step Description:
1. The admin logs into the admin interface.
2. The admin navigates to the league management page.
3. The admin can create, update, or delete leagues.

 Use Case: Update Scores
Brief Description: The admin updates live cricket scores.

Initial Step-By-Step Description:
1. The admin logs into the admin interface.
2. The admin navigates to the live scores management page.
3. The admin can manually update live scores if necessary.

 2.3. User Characteristics
- Users: Expected to be internet literate and familiar with cricket.
- Admins: Expected to be familiar with administrative tasks and have basic technical knowledge.

 2.4. Non-Functional Requirements
- The application must handle high traffic during major cricket events.
- The application must ensure real-time updates with minimal latency.
- The application must comply with data protection and privacy regulations.

 3.0. Requirements Specification

 3.1. External Interface Requirements
- The application must integrate with external APIs for live cricket scores.
- The application must provide RESTful APIs for third-party integrations.

 3.2. Functional Requirements

 3.2.1. Register User
Trigger: The user accesses the registration page.
Precondition: The registration page is displayed.
Basic Path:
1. The user fills in the registration form.
2. The system validates the information.
3. The system creates a new user account.
4. The system sends a confirmation email.

 3.2.2. Create Team
Trigger: The user logs into their account.
Precondition: The user is logged in.
Basic Path:
1. The user navigates to the team creation page.
2. The user selects players to form their team.
3. The system saves the team information.

 3.2.3. Join League
Trigger: The user logs into their account.
Precondition: The user is logged in.
Basic Path:
1. The user navigates to the league page.
2. The user selects a league to join.
3. The system adds the user to the league.

 3.2.4. View Live Scores
Trigger: The user logs into their account.
Precondition: The user is logged in.
Basic Path:
1. The user navigates to the live scores page.
2. The system displays live scores and updates them in real-time.

 3.2.5. Receive Notifications
Trigger: The user sets notification preferences.
Precondition: The user is logged in.
Basic Path:
1. The user sets notification preferences.
2. The system sends notifications based on the user’s preferences.

 3.2.6. Manage Users
Trigger: The admin logs into the admin interface.
Precondition: The admin is logged in.
Basic Path:
1. The admin navigates to the user management page.
2. The admin can add, update, or remove user accounts.

 3.2.7. Manage Leagues
Trigger: The admin logs into the admin interface.
Precondition: The admin is logged in.
Basic Path:
1. The admin navigates to the league management page.
2. The admin can create, update, or delete leagues.

 3.2.8. Update Scores
Trigger: The admin logs into the admin interface.
Precondition: The admin is logged in.
Basic Path:
1. The admin navigates to the live scores management page.
2. The admin can manually update live scores if necessary.

 3.3. Detailed Non-Functional Requirements

 3.3.1. Logical Structure of the Data

!Logical Structure of the Fantasy Cricket Data()

The logical structure of the data to be stored in the Fantasy Cricket Scorecard Application database is given below.

User Data Entity
- Name: Text - Name of the user
- Email Address: Text - User's email address
- Team: Pointer - Team entity

Team Data Entity
- Name: Text - Name of the team
- Owner: Pointer - User entity
- Players: List - List of player entities

League Data Entity
- Name: Text - Name of the league
- Participants: List - List of user entities

Score Data Entity
- Match ID: Integer - ID of the cricket match
- Runs: Integer - Runs scored
- Wickets: Integer - Wickets taken
- Overs: Float - Overs bowled

 3.3.2. Security
- The application must encrypt user data during transmission and storage.
- The application must implement role-based access control.
- Regular security audits and vulnerability assessments must be conducted.

 Index

- Fantasy Cricket: 1, 3, 4, 5, 6, 7, 8
- User: 1, 3, 4, 5, 6, 7, 8
- Admin: 1, 3, 4, 6, 7, 8
- League: 1, 3, 4, 5, 6, 7, 8
- Live Scores: 1, 3, 5, 6, 7, 8
- Registration: 3, 5, 7
- Team: 4, 5, 6, 7, 8
- Notification: 4, 5, 6, 7
- Security: 8
- API: 8


----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Here's an expanded, comprehensive SRS document with detailed explanations:

---

 Software Requirements Specification (SRS)
Fantasy Cricket Application
Version 2.0 | Date: [Insert Date]

---

 1. Introduction
 1.1 Purpose
This document serves as a comprehensive blueprint for the development of a Fantasy Cricket Application tailored for local/domestic cricket matches. It aims to:
- Define clear functional and non-functional requirements
- Establish a common understanding between stakeholders
- Guide the development team in system implementation
- Provide a basis for testing and validation

The application will enable users to create virtual teams, track real-time match performance, and compete in leaderboards while allowing administrators to manage match data and scoring systems.

---

 1.2 Document Conventions
- User: Refers to registered participants creating fantasy teams
- Admin: Refers to authorized personnel managing match data
- System: The combined frontend/backend infrastructure
- Fantasy Points: Virtual currency earned through player performance

All requirements marked SHALL are mandatory, while SHOULD indicates desirable but non-critical features.

---

 1.3 Intended Audience
- Development Team: For system design and implementation
- QA Engineers: For test case creation
- Project Managers: For milestone tracking
- UI/UX Designers: For interface refinement
- System Administrators: For deployment and maintenance

---

 1.4 Project Scope
Inclusions:
- User authentication system
- Fantasy team creation with budget constraints
- Real-time score tracking with WebSocket
- Automated fantasy points calculation
- Admin dashboard for match management
- Mobile-responsive design

Exclusions:
- Betting/payment features
- Video streaming capabilities
- Social media integration
- Machine learning predictions

---

 2. Overall Description
 2.1 Product Perspective
The system will consist of three interconnected components:
1. User Interface: Web-based responsive frontend
2. Application Server: Node.js/Express.js backend
3. Database: MySQL for persistent storage

Key Constraints:
- Must function during live matches without downtime
- Maintain data consistency across all clients
- Support 50+ concurrent users during peak hours

---

 2.2 Product Functions
Core Functionalities:
1. Fantasy Team Management:
   - Player selection from predefined squads
   - Credit-based team creation (100 credits max)
   - Role-based player filters (Batsman, Bowler, etc.)

2. Live Scoring System:
   - Ball-by-ball updates via admin interface
   - Automatic points calculation based on:
     - Runs scored (1 point per run)
     - Wickets taken (20 points per wicket)
     - Catches (10 points per catch)

3. Leaderboard System:
   - Real-time ranking updates
   - Seasonal/weekly leaderboards
   - Tie-breaker rules (Total boundaries scored)

---

 2.3 User Classes and Characteristics
| User Type | Description | Key Interactions |
|-----------|-------------|------------------|
| Regular User | Casual cricket enthusiast | - Team creation<br>- Score tracking<br>- Leaderboard viewing |
| Power User | Advanced fantasy player | - Multiple team management<br>- Detailed stats analysis |
| Administrator | Match organizer | - Player database management<br>- Live score updates<br>- Match scheduling |

---

 2.4 Operating Environment
Frontend:
- Supported Browsers: Chrome 90+, Firefox 85+, Safari 14+
- Responsive design for:
  - Mobile devices (320px+ width)
  - Tablets (768px+ width)
  - Desktops (1024px+ width)

Backend:
- Node.js v18.x
- Express.js v4.x
- MySQL 8.x
- Socket.io v4.x

Deployment:
- AWS EC2 instance (t3.medium)
- Docker containerization
- Load balancer for traffic management

---

 3. Specific Requirements
 3.1 Functional Requirements

 FR-01: User Registration System
- Description: Users SHALL create accounts using email/password
- Preconditions: Unique email not already registered
- Postconditions: Account created with verification email sent
- Process Flow:
  1. User enters email and password
  2. System validates inputs
  3. Database stores hashed password
  4. Verification email sent via SMTP

---

 FR-02: Fantasy Team Creation
- Description: Users SHALL select 11 players within 100 credits
- Rules:
  - Minimum 3 Batsmen, 2 Bowlers, 1 Wicketkeeper
  - Maximum 7 players from one team
- Validation:
  - Credit counter updates in real-time
  - Role-based warnings if requirements unmet

---

 3.2 Non-Functional Requirements

 Performance:
- P01: Page load time SHALL NOT exceed 2 seconds
- P02: Score updates SHALL reflect in <1 second across all clients
- P03: Support 100 concurrent score updates

 Security:
- S01: Passwords SHALL be hashed using BCrypt (12 rounds)
- S02: JWT tokens SHALL expire every 24 hours
- S03: SQL injection prevention via parameterized queries

 Usability:
- U01: Color contrast SHALL meet WCAG 2.1 AA standards
- U02: All features SHALL be accessible via keyboard navigation
- U03: Error messages SHALL be clear and actionable

---

 4. System Features
 4.1 Team Selection Interface
Description:
A drag-and-drop interface allowing users to build fantasy teams with visual feedback. Key elements:
- Player cards with role indicators
- Real-time credit counter
- Validation warnings for invalid team compositions

Example Workflow:
1. User selects "Create Team"
2. System shows available players with credit values
3. User selects 11 players (system enforces constraints)
4. Team saved with timestamp and versioning

---

 4.2 Real-Time Score Update System
Architecture:
- WebSocket Connection:
  - Socket.io implementation
  - Automatic reconnection with buffering
- Data Pipeline:
  1. Admin inputs ball-by-ball data
  2. Server processes fantasy points
  3. Clients receive updates via WebSocket

Update Protocol:
```json
{
  "event": "score_update",
  "data": {
    "match_id": 123,
    "runs": 4,
    "wicket": false,
    "over": "12.4"
  }
}
```

---

 5. User Interface Requirements
 5.1 Visual Design Principles
- Color Hierarchy:
  - Primary: Sky Blue (87CEEB) for interactive elements
  - Secondary: Deep Blue (005b96) for headers
  - Success: Emerald Green (4CAF50) for confirmations
  - Danger: Coral Red (ff6b6b) for errors

- Typography:
  - Roboto font family
  - 16px base font size
  - Clear hierarchy (H1: 32px, H2: 24px)

- Layout:
  - Grid-based responsive design
  - Maximum content width: 1280px
  - Mobile menu collapses at 768px

---

 5.2 Key Screens
1. Team Selection Screen:
- Player list with filters (role, team, price)
- Credit counter with progress bar
- Team composition validator with visual alerts

2. Live Score Screen:
- Dual team score comparison
- Ball-by-ball commentary display
- Real-time points calculation visualization

3. Admin Dashboard:
- Match timeline interface
- Player database with CSV import/export
- Score update interface with undo functionality

---

 6. Data Model
 6.1 Entity-Relationship Diagram
```mermaid
erDiagram
    USER ||--o{ FANTASY_TEAM : creates
    FANTASY_TEAM }o--|| PLAYER : contains
    MATCH ||--o{ SCORE_UPDATE : receives
    PLAYER }|--|| STATS : has
```

 6.2 Database Schema Details
users Table:
| Column | Type | Constraints | Description |
|--------|------|-------------|-------------|
| id | INT | PRIMARY KEY | Unique user identifier |
| username | VARCHAR(50) | UNIQUE | User's chosen handle |
| password_hash | VARCHAR(255) | NOT NULL | BCrypt hashed password |
| created_at | TIMESTAMP | DEFAULT CURRENT_TIMESTAMP | Account creation time |

fantasy_teams Table:
| Column | Type | Constraints |
|--------|------|-------------|
| id | INT | PRIMARY KEY |
| user_id | INT | FOREIGN KEY |
| match_id | INT | FOREIGN KEY |
| players | JSON | NOT NULL |
| total_points | INT | DEFAULT 0 |

---

 7. External Interface Requirements
 7.1 User Interfaces
- Login Screen:
  - Email/password fields with password visibility toggle
  - Forgot password link with CAPTCHA verification

- Team Builder:
  - Player cards with role icons and prices
  - Drag-and-drop functionality for team composition

---

 7.2 Admin Interfaces
- Score Update Console:
  - Numeric keypad for quick run entry
  - Wicket type selection (bowled, caught, etc.)
  - Undo last 3 balls feature

- Player Manager:
  - Bulk upload via Excel/CSV
  - Real-time validation of player data
  - Credit value adjustment interface

---

 8. Risk Analysis
| Risk | Probability | Impact | Mitigation Strategy |
|------|-------------|--------|---------------------|
| Real-time data delay | High | Critical | Implement Redis caching layer |
| Data inconsistency | Medium | High | Database transaction rollbacks |
| Security breaches | Low | Catastrophic | Regular security audits |
| Mobile responsiveness issues | Medium | Medium | Cross-device testing matrix |

---

 9. Future Roadmap
Phase 1 (MVP):
- Core fantasy features
- Basic admin controls

Phase 2 (Enhancements):
- AI-powered team suggestions
- Multi-match support
- Player performance projections

Phase 3 (Advanced):
- Mobile app development
- Social sharing capabilities
- Advanced analytics dashboard

---

Appendices:
- A: UI Mockups (Attached)
- B: API Endpoint Specifications
- C: Technology Stack Documentation
- D: Compliance Requirements (GDPR)

Would you like me to expand any specific section further or add additional technical details? 📝
