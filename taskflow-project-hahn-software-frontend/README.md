# TaskFlow Frontend - Vue 3 + TypeScript + Vite

A modern task management application built with Vue 3, TypeScript, and Vite.

## 🛠️ Tech Stack

- **Frontend Framework**: Vue 3 (Composition API)
- **Language**: TypeScript
- **Build Tool**: Vite
- **State Management**: Pinia
- **Routing**: Vue Router
- **HTTP Client**: Axios
- **Styling**: Tailwind CSS
- **Icons**: Lucide Vue Next

## 📋 Prerequisites

- Node.js 18+ and npm/yarn
- Backend API running on `http://localhost:8080`

## 🚀 Getting Started

### 1. Install Dependencies

```bash
npm install
```

### 2. Start Development Server

```bash
npm run dev
```

The application will be available at `http://localhost:3000`

### 3. Build for Production

```bash
npm run build
```

## 📁 Project Structure

```
src/
├── api/              # API client and endpoints
│   ├── axios.ts      # Axios configuration with interceptors
│   ├── auth.ts       # Authentication API
│   ├── projects.ts   # Projects API
│   ├── tasks.ts      # Tasks API
│   └── members.ts    # Team members API
├── stores/           # Pinia state management
│   ├── auth.ts       # Authentication store
│   ├── projects.ts   # Projects store
│   └── tasks.ts      # Tasks store
├── views/            # Page components
│   ├── LoginView.vue
│   ├── RegisterView.vue
│   ├── ProjectsView.vue
│   └── ProjectDetailView.vue
├── router/           # Vue Router configuration
│   └── index.ts
├── types/            # TypeScript interfaces
│   └── index.ts
├── App.vue           # Root component
├── main.ts           # Application entry point
└── style.css         # Global styles and Tailwind

## ✨ Features

### Authentication
- User registration with name, email, and password
- Login with JWT token authentication
- Automatic token management and storage
- Protected routes with authentication guards
- Auto-redirect on 401 responses

### Projects Management
- Create new projects with title and description
- View all user projects in a card grid layout
- Beautiful empty states with call-to-action
- Real-time project progress tracking
- Navigate to project details

### Tasks Management
- Create tasks with title, description, and due date
- Mark tasks as complete/incomplete with checkbox
- Delete tasks with confirmation
- View all tasks for a project
- Beautiful task list with status indicators
- Due date display with calendar icons

### Progress Tracking
- Visual progress bar for each project
- Percentage completion calculation
- Total tasks and completed tasks count
- Real-time updates on task status changes

### UI/UX
- Clean, modern interface with Tailwind CSS
- Responsive design (mobile, tablet, desktop)
- Loading states and error handling
- Smooth transitions and animations
- Icon system with Lucide icons
- Empty states with helpful guidance

## 🔧 Configuration

### Backend API URL

The frontend is configured to proxy API requests to `http://localhost:8080`. You can change this in `vite.config.ts`:

```typescript
server: {
  port: 3000,
  proxy: {
    '/api': {
      target: 'http://localhost:8080', // Change this to your backend URL
      changeOrigin: true,
    },
  },
},
```

### Environment Variables

For production deployment, you can set the backend URL using environment variables:

```env
VITE_API_URL=https://your-backend-api.com
```

Then update `src/api/axios.ts`:

```typescript
const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL || '/api',
  // ...
})
```

## 🎨 Customization

### Colors

The primary color scheme can be customized in `tailwind.config.js`:

```javascript
theme: {
  extend: {
    colors: {
      primary: {
        // Change these values to customize the primary color
        500: '#0ea5e9',
        600: '#0284c7',
        700: '#0369a1',
      }
    }
  }
}
```

### Fonts

Add custom fonts in `src/style.css` or modify the Tailwind configuration.

## 🐛 Troubleshooting

### CORS Issues
If you encounter CORS errors, make sure your backend is configured to allow requests from `http://localhost:3000`.

### Authentication Errors
- Verify the backend is running on port 8080
- Check that JWT tokens are being properly set in headers
- Ensure localStorage is not blocked in your browser

### Build Errors
- Clear `node_modules` and reinstall: `rm -rf node_modules package-lock.json && npm install`
- Ensure you're using Node.js 18 or higher

## 📝 Notes for Internship Submission

This frontend application demonstrates:
- ✅ Modern Vue 3 Composition API with TypeScript
- ✅ Clean architecture with separation of concerns
- ✅ State management with Pinia
- ✅ API integration with proper error handling
- ✅ Authentication with JWT tokens
- ✅ Full CRUD operations for Projects and Tasks
- ✅ Progress tracking with visual indicators
- ✅ Responsive design with Tailwind CSS
- ✅ Professional UI/UX with loading states and empty states
- ✅ Type-safe development with TypeScript

## 📹 Demo Video

[Include your demo video link here]

## 👤 Author

[Your Name]
[Your Email]

## 📄 License

MIT
