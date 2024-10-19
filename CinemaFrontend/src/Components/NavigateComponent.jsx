import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import "../styles/navigate.css";
import Logo from "../images/logo.png";
import { AiOutlineMenuFold } from "react-icons/ai";
import { MdCancel } from "react-icons/md";

function NavigateComponent() {
  const [isPanelVisible, setPanelVisible] = useState(false);

  useEffect(() => {
    const allLi = document.querySelector(".menuList").querySelectorAll("li");

    function changeMenuActive() {
      allLi.forEach((n) => n.classList.remove("active"));
      this.classList.add("active");
    }

    allLi.forEach((n) => n.addEventListener("click", changeMenuActive));

    allLi[0].classList.add("active");
  }, []);

  const togglePanel = () => {
    setPanelVisible(!isPanelVisible);
  };

  return (
      <div className="navigateComponent">

        <i className="iMenu" onClick={togglePanel}>
          {isPanelVisible ? <MdCancel /> : <AiOutlineMenuFold/>}
        </i>
        <div className="logo">
          <img src={Logo} alt=""/>
          <p>CinemaManagment</p>
        </div>

        <div className={`panel ${isPanelVisible ? "visible" : ""}`}>

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
            <img src="" alt=""/>
            <p>Imie Nazwisko</p>
            <i>
              <AiOutlineMenuFold/>
            </i>
          </div>
        </div>

        <div className="bottom"></div>
      </div>
  );
}

export {NavigateComponent};
