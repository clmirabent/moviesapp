# 🎥 MovieApp  

This project consists in a small full-stack web application to search for movies, manage favorites, and handle user authentication for a technical assigment.The user can search a movie online if is logged in, can add favorite´s movies and delete them from the list.
<img width="1276" alt="Screenshot 2025-06-27 at 17 16 47" src="https://github.com/user-attachments/assets/44b14087-4cac-4f42-b9be-3f0089ab2ad7" />

---
## Demo 
[![Take a look to the video](<img width="1276" alt="Screenshot 2025-06-27 at 17 16 47" src="https://github.com/user-attachments/assets/6ab2f293-a97f-49f5-9dc7-0cb3285be966" />
)](https://youtu.be/nMalsbzAsnA)

---
## Technologies Used

### Frontend
- **React 19** with TypeScript
- **Vite** for fast development and build
- **React Router DOM** for routing
- **React Hook Form** for form management
- **Axios** for HTTP requests
- **React Toastify** for notifications
- **CSS Modules** for styling

### Backend
- **Java Spring Boot 6** 
- Java 24
- **JWT** for authenticatio
- **Database**: H2 in-memory database.

---

## 🖥️ Setup Instructions

### Prerequisites
- Java 24
- npm or yarn
- Backend API running (see backend instructions)

### Frontend

1. **Clone the repository on your computer:**
   ```sh
   git clone https://github.com/<USERNAME>/moviesapp.git
   cd frontend
   ```

2. **Install dependencies:**
   ```sh
   npm install
   # or
   yarn install
   ```

3. **Configure environment variables:**
   - Edit `.env.local` and set `VITE_API_URL` to your backend API URL (default: `http://localhost:8080/api`).

4. **Run the development server:**
   ```sh
   npm run dev
   # or
   yarn dev
   ```

5. **Open the app:**
   - Visit [http://localhost:5173](http://localhost:5173) (or the port shown in your terminal).

### Backend
**Note: Backend lives in a separate repo — make sure it's running at VITE_API_URL.
> Please ensure you have a compatible backend running that exposes the following endpoints:
> - `POST /api/auth/login`
> - `POST /api/auth/register`
> - `GET /api/movies?title=...`
> - `GET /api/favorites`
> - `POST /api/favorites`
> - `DELETE /api/favorites`

1. Ensure  you have the repository locally .
2. Change current directory to `backend` directory
   ```sh
   cd backend
   ```
4. Install dependencies:
   ```sh
   ./gradlew build
   ```
5. Configure environment in `backend/src/main/resources/application.properties` (e.g., database URL, JWT secret).
6. Start the backend server:
   ```sh
   ./gradlew bootRun
   ```
7. Ensure it runs on the same URL as set in `VITE_API_URL`.

---

## 📝 Limitations
- **Rapid Turnaround:** Delivered the complete project in an intensive 24–48 hour sprint.  
- **Self-Taught Java:** Embraced Java for the first time and built the backend from scratch.

---

## 💡Improvements with More Time

- Implement a robust search (multi-result, pagination, filters).
- Add user profile management
- Make the UI fully responsive and accessible.
- Implement OAuth (Google, Facebook) login.
- Deploy the app and provide a live demo link.
- Improve error handling and user feedback.
- Add unit and integration tests.
- Improve performance


---

