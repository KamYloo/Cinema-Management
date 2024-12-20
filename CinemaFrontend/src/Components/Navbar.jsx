import React, { useState, useEffect } from "react";
import { Link, useNavigate} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import "../styles/navigate.css";
import Logo from "../assets/logo.png";
import { AiOutlineMenuFold } from "react-icons/ai";
import { MdCancel } from "react-icons/md";
import { FaUserCircle } from "react-icons/fa";
import {logoutAction} from "../Redux/AuthService/Action.js";
import toast from "react-hot-toast";

function Navbar({ activeTab, setActiveTab }) {
  const [isPanelVisible, setPanelVisible] = useState(false);
  const [menu, setMenu] = useState(false)
  const {auth} = useSelector(store => store);
  const dispatch = useDispatch()
  const navigate = useNavigate()

  const handleLogout = () => {
    dispatch(logoutAction())
        .then(() => {
          navigate("/login");
          toast.success('Logged out successfully');
        })
        .catch(() => {
          toast.error("Failed to logout. Please try again.");
        })
        .finally(()=> {
          setMenu(null)
        })
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
        <div className="logo" onClick={() =>  navigate('/home')}>
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
          <i className="userIcon"><FaUserCircle /></i>
            {!localStorage.getItem("token") ? (
                <p style={{ cursor: 'pointer' }} onClick={() => navigate('/login')}>Login</p>
            ) : (
                <>
                  <p>{auth.reqUser?.fullName}</p>
                  <i onClick={() => setMenu((prev) => !prev)}>
                    <AiOutlineMenuFold/>
                  </i>
                  {(menu || isPanelVisible) && (
                      <ul>
                        <li onClick={() => {
                          navigate(`/reservations/user/${auth.reqUser?.id}`)
                          setMenu(null)
                        }}>Reservations</li>
                        <li onClick={handleLogout}>Logout</li>
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

export {Navbar};
