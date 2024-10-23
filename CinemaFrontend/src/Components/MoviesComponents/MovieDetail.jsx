import React, { useState } from "react";
import { useNavigate } from 'react-router-dom'
import "../../styles/movieDetail.css";
import { FaCirclePlay } from "react-icons/fa6";
import { PiFilmReelDuotone } from "react-icons/pi";

function MovieDetail() {
    const navigate = useNavigate();

    return (
        <div className="movieDetail">
            <div className="backGround"></div>
            <div className="center">
                <div className="up">
                <img src="https://m.media-amazon.com/images/M/MV5BYjY2NDZhN2EtZmYwNS00NDU2LThiODAtNmY4OWY2ODNmZWRmXkEyXkFqcGc@._V1_.jpg" alt="" />
                    <div className="data">
                        <h3><i><FaCirclePlay/></i>fiLmdsfdsivjos</h3>
                        <p>Animation <span>· 90min</span></p>
                        <div className="description">
                            <p>Description</p>
                            <span>
                                sgfdsfDSFGSGVDFVXZVFSGfdagdvdahgdahfdavbadvresdfffffffffffffffffffffffffffffffffffffffffffffffffffffffsdfffffffffffffffffffffffffffffffffhgfdagadgfbdabkiuhfdavhfdiuyvhdfiuvbhdaiffvbdaovi8fyubhavdldaifuvgbdalivf
                            </span>
                        </div>
                    </div>
                </div>
                <div className="showTimes">
                    <h4>Available Showtimes</h4>
                    <ul>
                        <li onClick={()=>navigate("seats")}>
                            <button className="showTime">
                                <div className="info">
                                    <span className="date">Oct 23, 2024</span>
                                    <span className="time">18:30</span>
                                </div>
                                <i><PiFilmReelDuotone/></i>
                            </button>
                        </li>
                        <li>
                            <button className="showTime">
                                <div className="info">
                                    <span className="date">Oct 23, 2024</span>
                                    <span className="time">18:30</span>
                                </div>
                                <i><PiFilmReelDuotone/></i>
                            </button>
                        </li>
                        <li>
                            <button className="showTime">
                                <div className="info">
                                    <span className="date">Oct 23, 2024</span>
                                    <span className="time">18:30</span>
                                </div>
                                <i><PiFilmReelDuotone/></i>
                            </button>
                        </li>
                        <li>
                            <button className="showTime">
                                <div className="info">
                                    <span className="date">Oct 23, 2024</span>
                                    <span className="time">18:30</span>
                                </div>
                                <i><PiFilmReelDuotone/></i>
                            </button>
                        </li>
                        <li>
                            <button className="showTime">
                                <div className="info">
                                    <span className="date">Oct 23, 2024</span>
                                    <span className="time">18:30</span>
                                </div>
                                <i><PiFilmReelDuotone/></i>
                            </button>
                        </li>
                        <li>
                            <button className="showTime">
                                <div className="info">
                                    <span className="date">Oct 23, 2024</span>
                                    <span className="time">18:30</span>
                                </div>
                                <i><PiFilmReelDuotone/></i>
                            </button>
                        </li>
                        <li>
                            <button className="showTime">
                                <div className="info">
                                    <span className="date">Oct 23, 2024</span>
                                    <span className="time">18:30</span>
                                </div>
                                <i><PiFilmReelDuotone/></i>
                            </button>
                        </li>
                        <li>
                            <button className="showTime">
                                <div className="info">
                                    <span className="date">Oct 23, 2024</span>
                                    <span className="time">18:30</span>
                                </div>
                                <i><PiFilmReelDuotone/></i>
                            </button>
                        </li>
                        <li>
                            <button className="showTime">
                                <div className="info">
                                    <span className="date">Oct 23, 2024</span>
                                    <span className="time">18:30</span>
                                </div>
                                <i><PiFilmReelDuotone/></i>
                            </button>
                        </li>
                        <li>
                            <button className="showTime">
                                <div className="info">
                                    <span className="date">Oct 23, 2024</span>
                                    <span className="time">18:30</span>
                                </div>
                                <i><PiFilmReelDuotone/></i>
                            </button>
                        </li>


                    </ul>
                </div>
            </div>
        </div>
    );
}

export {MovieDetail};
