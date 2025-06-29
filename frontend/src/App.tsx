import React from "react";
import { Route, Routes } from "react-router-dom";
import LoginForm from "./pages/Login/Login";
import Home from "./pages/Home/Home";
import Register from "./pages/Register/Register";
import Favorites from "./pages/Favorites/Favorites";
import { ToastContainer, toast } from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';

toast.success('Success!', {
  icon: <span style={{ color: '#88C7B4' }}>✔</span>,
});


const App = () => (
  <div>
    <ToastContainer 
        position="top-center" 
        autoClose={3000} 
        hideProgressBar 
        pauseOnHover={false} 
      />
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/login" element={<LoginForm />} />
      <Route path="/register" element={<Register />} />
      <Route path="/favorites" element={<Favorites />} />
    </Routes>
      
  </div>
);

export default App;

