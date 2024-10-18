import './App.css'
import React, { useEffect, useState } from 'react'
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import {NavigateComponent} from "./Components/NavigateComponent.jsx";
import {Home} from "./Components/HomeComponents/Home.jsx";
import {MoviesView} from "./Components/MoviesComponents/MoviesView.jsx";

function App() {

    const renderLayout = (Component) => {

        return (
            <div className='App'>
                <NavigateComponent />
                <Component/>
            </div>
        )
    }

  return (
      <Router>
          <Routes>

              {/*<Route path="/login" element={
                  <div className='loginLayout'><Login />
                      <div className="background"></div>
                  </div>}
              />

              <Route path="/register" element={
                  <div className="loginLayout"><Register />
                      <div className="background"></div>
                  </div>
              } />*/}

              <Route path="/home" element={
                  renderLayout(Home)
              } />

              <Route path="/movies" element={
                  renderLayout(MoviesView)
              } />

              <Route path="/" element={<Navigate to="/home" />} />
          </Routes>
      </Router>
  )
}

export default App
