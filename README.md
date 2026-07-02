# CarCompass 🚗🧭

CarCompass is an intelligent car recommendation engine that helps users find their perfect vehicle through a dynamic questionnaire and keyword-matching algorithm. It also features a full car catalog, admin management panel, and user authentication.

---

## 1. What did you build and why? What did you deliberately cut?

**Built:**
- **Recommendation Engine:** A dynamic questionnaire that maps user answers to car tags via a keyword-matching algorithm, returning a ranked list of cars based on a match percentage.
- **Admin Dashboard:** A fully functional admin panel to perform CRUD operations on cars and questionnaire questions, including uploading and streaming images directly from the database.
- **Browse & Search:** A catalog page with real-time text search and multi-criteria sorting.
- **Auth & Security:** JWT-based stateless authentication with role-based access control (Admin vs. User).

**Deliberately Cut:**
- **Complex Machine Learning Model:** Instead of a complex ML or vector-based recommendation system, we opted for a robust, explainable tag-matching algorithm to get a working prototype up quickly.
- **User Reviews System:** While "Trending Cars" was implemented (using a proxy metric of safety + mileage), a full user-submitted reviews and ratings subsystem was cut to focus on the core recommendation experience.
- **Frontend Frameworks:** We cut React/Vue/Angular in favor of vanilla HTML/CSS/jQuery to completely eliminate build steps, Webpack config, and Node dependencies, allowing the frontend to be served instantly by Spring Boot.

## 2. What’s your tech stack and why did you pick it?

- **Backend:** Java 17 + Spring Boot 3
  - *Why:* Unmatched ecosystem for enterprise-grade APIs, rapid development with Spring Data, and out-of-the-box Security.
- **Database:** MongoDB + GridFS
  - *Why:* The flexible schema is perfect for cars (which have highly variable specifications) and dynamic questionnaire options. GridFS allows us to store car images directly alongside our data without needing a separate S3 bucket or cloud storage provider.
- **Migrations:** Mongock
  - *Why:* Ensures the database is always seeded with initial questions, users, and car data upon startup, making it incredibly easy to set up on a new machine.
- **Frontend:** Vanilla HTML, CSS, jQuery
  - *Why:* Zero configuration. It allows for rapid prototyping of a multi-page application with a sleek UI directly within the `src/main/resources/static` directory. 

## 3. What did you delegate to AI tools vs. do manually? Where did the tools help most?
**Manual Work:**
- **Architecture Design:** I designed the initial architecture and choose what to do and what to not. 
- **Prioritising work**: In what ways the execution will occur
- **Deployment Config**: By the mean time connected github to RAILWAY app for automatic deployment


**Delegated to AI:**
- **UI/UX Design:** The entire dark-mode, glassmorphic CSS design system, responsive layouts, and DOM manipulation scripts.
- **Boilerplate & Plumbing:** Setting up Spring Security with JWT filters, creating DTOs, controllers, and MongoDB repositories.
- **Complex UI Logic:** The Admin panel's dynamic form builders (adding variable amounts of options to questions, drag-and-drop image uploads).
- **Database Seeding:** Generating a realistic catalog of sample cars and a logical questionnaire flow in the Mongock migration.

**Where AI helped most:**
- **Rapid Iteration:** Building the 800+ line Admin panel (HTML/CSS/JS) in a single shot would take hours manually; the AI scaffolded it in seconds.
- **Debugging:** Quickly identifying that GridFS required converting a String ID to a BSON `ObjectId` and explicitly setting the `InputStreamResource` to stream images properly.

## 4. Where did AI tools get in the way?

- **Context Loss on Large Files:** When modifying very large frontend files (like `admin.html`), the AI occasionally needed to view specific line ranges multiple times to ensure it didn't overwrite existing logic, which slightly slowed down the flow compared to a human using an IDE.
- **Sequential Tooling:** Debugging the GridFS image rendering required a few round trips to fix the service, then the controller, then the UI, whereas a human might have touched all three simultaneously.

## 5. If you had another 4 hours, what would you add?

1. **Vector Search Recommendations:** I would replace the keyword-matching algorithm with an embedding model (like OpenAI or local LLM) and use Elastic Vector Search to match user preferences to cars semantically.
2. **User Profiles & Saved Cars:** Allow authenticated users to save their favorite cars, view past recommendation results, and compare cars side-by-side.
3. **Comprehensive Testing:** Add comprehensive JUnit/Mockito tests for the recommendation algorithm, and integration tests for the REST endpoints using `@SpringBootTest`.
4. **Frontend Modernization:** Migrate the vanilla JS/jQuery frontend to React to improve state management and component reusability as the app scales.
