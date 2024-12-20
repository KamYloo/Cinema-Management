import './App.css'
import React, { useEffect, useState } from 'react'
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import {Navbar} from "./Components/Navbar.jsx";
import {Home} from "./Components/HomeComponents/Home.jsx";
import {MoviesView} from "./Components/MoviesComponents/MoviesView.jsx";
import {Login} from "./Components/AuthComponents/Login.jsx";
import {Register} from "./Components/AuthComponents/Register.jsx";
import {AddMovie} from "./Components/MoviesComponents/AddMovie.jsx";
import {useDispatch} from "react-redux";
import {currentUserAction} from "./Redux/AuthService/Action.js";
import {MovieDetail} from "./Components/MoviesComponents/MovieDetail.jsx";
import {SeatsView} from "./Components/SeatsComponents/SeatsView.jsx";
import {ReservationsView} from "./Components/ReservationComponents/ReservationsView.jsx";
import {Toaster} from "react-hot-toast";


function App() {
    const [activeTab, setActiveTab] = useState("home");
    const dispatch = useDispatch();

    useEffect(() => {
        dispatch(currentUserAction())
    }, [dispatch])

    const renderLayout = (Component) => {

        return (
            <div className='App'>
                <Navbar activeTab={activeTab} setActiveTab={setActiveTab}/>
                <Component setActiveTab={setActiveTab}/>
            </div>
        )
    }

  return (
      <Router>
          <Routes>

              <Route path="/login" element={
                  <div className='loginLayout'><Login />
                      <div className="background"></div>
                  </div>}
              />

              <Route path="/register" element={
                  <div className="loginLayout"><Register />
                      <div className="background"></div>
                  </div>
              } />

              <Route path="/home" element={
                  renderLayout(Home)
              } />

              <Route path="/movies" element={
                  renderLayout(MoviesView)
              } />

              <Route path="/movies/add" element={
                  renderLayout(AddMovie)
              } />

              <Route path="/movies/movie/:movieId" element={
                  renderLayout(MovieDetail)
              } />

              <Route path="/movies/movie/:movieId/showTime/:showTimeId/seats" element={
                  renderLayout(SeatsView)
              } />

              <Route path="/reservations/user/:userId" element={
                  renderLayout(ReservationsView)
              } />

              <Route path="/" element={<Navigate to="/home" />} />
          </Routes>
          <Toaster
              position="bottom-right"
              reverseOrder={false}
          />
      </Router>
  )
}

export default App
