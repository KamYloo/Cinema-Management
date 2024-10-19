import React, { useState } from "react";
import { useNavigate } from 'react-router-dom'
import "../../styles/home.css";
import backImg from "../../images/homeImg.png";

function Home({ setActiveTab }) {

    const navigate = useNavigate();

  return (
    <div className="homeView">
      <img className="backImg" src={backImg} alt="" />
      <div className="mainBox">
        <h2>Cinema Managment</h2>
        <h1>CineFlex</h1>
        <p>Suggests flexibility and versatility in managing cinema operations. Ideal for an app focused on providing a seamless cinema experience.</p>
        <button type="button" onClick={() =>
                {navigate("/movies")
                setActiveTab("movies")
        }}>Make Reservation</button>
      </div>
    </div>
  );
}

export { Home };
