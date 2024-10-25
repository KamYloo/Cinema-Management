import React, { useState, useEffect } from "react";
import { Link, useNavigate} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import "../styles/navigate.css";
import Logo from "../images/logo.png";
import { AiOutlineMenuFold } from "react-icons/ai";
import { MdCancel } from "react-icons/md";
import {logoutAction} from "../Redux/Auth/Action.js";
import {BASE_API_URL} from "../config/api.js";

function NavigateComponent({ activeTab, setActiveTab }) {
  const [isPanelVisible, setPanelVisible] = useState(false);
  const [menu, setMenu] = useState(false)
  const {auth} = useSelector(store => store);
  const dispatch = useDispatch()
  const navigate = useNavigate()

  const handleLogout = () => {
    dispatch(logoutAction())
    navigate('/login')
  }

  useEffect(() => {
    const allLi = document.querySelector(".menuList").querySelectorAll("li");

    function changeMenuActive(event) {
      allLi.forEach((n) => n.classList.remove("active"));
      event.currentTarget.classList.add("active");
    }

    allLi.forEach((li, index) => {
      li.classList.remove("active");
      if (li.textContent.toLowerCase() === activeTab) {
        li.classList.add("active");
      }
    });
  }, [activeTab]);

  const togglePanel = () => {
    setPanelVisible(!isPanelVisible);
  };

  return (
      <header className="navigateComponent">
        <i className="iMenu" onClick={togglePanel}>
          {isPanelVisible ? <MdCancel/> : <AiOutlineMenuFold/>}
        </i>
        <div className="logo">
          <img src={Logo} alt=""/>
          <p>CineFlex</p>
        </div>

        <div className={`panel ${isPanelVisible ? "visible" : ""}`}>
          <nav className="menuList">
            <ul>
              <li onClick={() => setActiveTab("home")}>
                <Link to="/home">Home</Link>
              </li>
              <li onClick={() => setActiveTab("movies")}>
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
          <img src={`${BASE_API_URL}/${auth.reqUser?.profilePicture || ''}`} alt="Profile" />
            {!auth.reqUser ? (
                <p style={{ cursor: 'pointer' }} onClick={() => navigate('/login')}>Login</p>
            ) : (
                <>
                  <p>{auth.reqUser?.fullName}</p>
                  <i onClick={() => setMenu((prev) => !prev)}>
                    <AiOutlineMenuFold/>
                  </i>
                  {(menu || isPanelVisible) && (
                      <ul>
                        <li onClick={handleLogout}>Logout</li>
                        <li onClick={() => navigate(`/reservations/user/${auth.reqUser?.id}`)}>Reservations</li>
                      </ul>
                  )}
                </>
            )}
          </div>
        </div>

        <div className="bottom"></div>
      </header>
  );
}

export {NavigateComponent};
