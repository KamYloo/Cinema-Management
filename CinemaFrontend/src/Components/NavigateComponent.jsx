import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import "../styles/navigate.css";
import Logo from "../images/logo.png";
import { AiOutlineMenuFold } from "react-icons/ai";

function NavigateComponent() {
  useEffect(() => {
    const allLi = document.querySelector(".menuList").querySelectorAll("li");

    function changeManuActive() {
      allLi.forEach((n) => n.classList.remove("active"));
      this.classList.add("active");
    }

    allLi.forEach((n) => n.addEventListener("click", changeManuActive));

    allLi[0].classList.add("active");
  }, []);

  return (
    <div className="navigateComponent">
      <div className="logo">
        <img src={Logo} alt="" />
        <p>CinemaManagment</p>
      </div>

      <div className="panel">
        <nav className="menuList">
          <ul>
            <li>
              <Link to="/home">Home</Link>
            </li>
            <li>
              <Link to="/movies">Movies</Link>
            </li>
            <li>
              <a href="#">About</a>
            </li>
            <li>
              <a href="#">Help</a>
            </li>
          </ul>
        </nav>
        <div className="profileBox">
          <img src="" alt="" />
          <p>Imie Nazwisko</p>
          <i>
            <AiOutlineMenuFold />
          </i>
        </div>
      </div>

      <div className="bottom"></div>
    </div>
  );
}

export { NavigateComponent };
