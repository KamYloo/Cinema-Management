import React, {useEffect, useState} from "react";
import {useNavigate, useParams} from 'react-router-dom'
import "../../styles/movieDetail.css";
import { FaCirclePlay } from "react-icons/fa6";
import { PiFilmReelDuotone } from "react-icons/pi";
import {useDispatch, useSelector} from "react-redux";
import {getMovie} from "../../Redux/Movie/Action.js";
import {getShowTimes} from "../../Redux/ShowTime/Action.js";

function MovieDetail() {
    const {movieId} = useParams();
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const {movie, showTime} = useSelector(store => store);


    const convertShowtime = (dateTimeString) => {
        const dateTime = new Date(dateTimeString);
        const options = { month: 'short', day: '2-digit', year: 'numeric' };
        const formattedDate = dateTime.toLocaleDateString('en-US', options);
        const formattedTime = dateTime.toLocaleTimeString('en-US', {
            hour: '2-digit',
            minute: '2-digit',
            hour12: false
        });

        return {
            date: formattedDate,
            time: formattedTime
        };
    };

    useEffect(() => {
        dispatch(getMovie(movieId))
    }, [dispatch, movieId]);

    useEffect(() => {
        dispatch(getShowTimes(movieId))
    }, [dispatch,movieId])

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
                        {showTime.showTimes?.map((item) => (
                            <li  key={item.id} onClick={() => navigate("seats")} >
                                <button className="showTime">
                                    <div className="info">
                                        <span className="date">{convertShowtime(item?.time).date}</span>
                                        <span className="time">{convertShowtime(item?.time).time}</span>
                                    </div>
                                    <i><PiFilmReelDuotone/></i>
                                </button>
                            </li>
                        ))}
                    </ul>
                </div>
            </div>
        </div>
    );
}

export {MovieDetail};
