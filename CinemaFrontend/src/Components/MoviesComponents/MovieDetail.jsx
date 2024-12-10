import React, {useEffect} from "react";
import {useNavigate, useParams} from 'react-router-dom'
import "../../styles/movieDetail.css";
import { FaCirclePlay } from "react-icons/fa6";
import { PiFilmReelDuotone } from "react-icons/pi";
import {useDispatch, useSelector} from "react-redux";
import {deleteMovieAction, getMovieAction} from "../../Redux/MovieService/Action.js";
import {getShowTimesAction} from "../../Redux/ShowTimeService/Action.js";
import {convertShowtime} from "../../utils/formatDate.js";
import toast from "react-hot-toast";

function MovieDetail() {
    const {movieId} = useParams();
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const {movie, showTime} = useSelector(store => store);

    useEffect(() => {
        dispatch(getMovieAction(movieId));
    }, [dispatch, movieId]);

    useEffect(() => {
        dispatch(getShowTimesAction(movieId));
    }, [dispatch, movieId]);

    const handleDelete = () => {
        if (window.confirm("Are you sure you want to delete this movie?")) {
            dispatch(deleteMovieAction(movieId))
                .then(() => {
                    toast.success("MovieService deleted successfully.");
                    navigate("/movies");
                })
                .catch(error => {
                    toast.error("Error deleting movie: " + error.message);
                });
        }
    };

    return (
        <div className="movieDetail">
            <div className="backGround"></div>
            <div className="center">
                <div className="up">
                    <img src={movie.getMovie?.image} alt="" />
                    <div className="data">
                        <h3><i><FaCirclePlay /></i>{movie.getMovie?.title}</h3>
                        <div className="data-x">
                            <p>{movie.getMovie?.genre} <span>· {movie.getMovie?.duration}min</span></p>
                            <button className="deleteButton" onClick={handleDelete}>
                                Delete Movie
                            </button>
                        </div>
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
                            <li key={item.id} onClick={() => navigate(`showTime/${item.id}/seats`)}>
                                <button className="showTime">
                                    <div className="info">
                                        <span className="date">{convertShowtime(item?.time).date}</span>
                                        <span className="time">{convertShowtime(item?.time).time}</span>
                                    </div>
                                    <i><PiFilmReelDuotone /></i>
                                </button>
                            </li>
                        ))}
                    </ul>
                </div>
            </div>
        </div>
    );
}

export { MovieDetail };
