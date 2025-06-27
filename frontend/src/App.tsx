import React from "react";
import { Route, Routes } from "react-router-dom";
import LoginForm from "./pages/Login/Login";
import Home from "./pages/Home/Home";
import Register from "./pages/Register/Register";
import Favorites from "./pages/Favorites/Favorites";

const App = () => (
  <div>
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/login" element={<LoginForm />} />
      <Route path="/register" element={<Register />} />
      <Route path="/favorites" element={<Favorites />} />
    </Routes>
  </div>
);

export default App;

