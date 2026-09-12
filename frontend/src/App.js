import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import Navbar from './components/Navbar';
import Home from './components/Home';

function App() {
  return (
    <Router>
      <div className="App">
        <Navbar />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/login" element={<div className="container mt-5 text-center">Login Page Placeholder</div>} />
          <Route path="/cart" element={<div className="container mt-5 text-center">Cart Page Placeholder</div>} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
