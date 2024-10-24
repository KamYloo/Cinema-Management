import React, {useEffect, useState} from "react";
import {useNavigate, useParams} from 'react-router-dom'
import "../../styles/movieDetail.css";
import { FaCirclePlay } from "react-icons/fa6";
import { PiFilmReelDuotone } from "react-icons/pi";
import {useDispatch, useSelector} from "react-redux";
import {getMovie} from "../../Redux/Movie/Action.js";

function MovieDetail() {
    const {movieId} = useParams();
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const {movie} = useSelector(store => store);

    useEffect(() => {
        dispatch(getMovie(movieId))
    }, [dispatch, movieId]);

    return (
        <div className="movieDetail">
            <div className="backGround"></div>
            <div className="center">
                <div className="up">
                <img src={movie.getMovie?.image} alt="" />
                    <div className="data">
                        <h3><i><FaCirclePlay/></i>{movie.getMovie?.title}</h3>
                        <p>{movie.getMovie?.genre} <span>· {movie.getMovie?.duration}min</span></p>
                        <div className="description">
                            <p>Description</p>
                            <span>
                               {movie.getMovie?.description}
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
