import React, {useEffect, useState} from "react";
import {useNavigate, useParams} from 'react-router-dom'
import "../../styles/seats.css";
import { PiArmchairBold } from "react-icons/pi";
import {useDispatch, useSelector} from "react-redux";
import {getMovie} from "../../Redux/Movie/Action.js";
import {getShowTime} from "../../Redux/ShowTime/Action.js";

function SeatsView() {

    const {showTimeId} = useParams();
    const dispatch = useDispatch();
    const {showTime} = useSelector(store => store);
    const navigate = useNavigate();

   /* const rows = seats.reduce((acc, seat) => {
        acc[seat.rowNumber] = acc[seat.rowNumber] || [];
        acc[seat.rowNumber].push(seat);
        return acc;
    }, {});*/

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
        dispatch(getShowTime(showTimeId))
    }, [dispatch, showTimeId]);

    return (
        <div className="seatsView">
            <div className="center">
                <div className="up">
                    <div className="top">
                        <p>Watch and Enjoy</p>
                        <p>{convertShowtime(showTime.getShowTime?.time).date} · {convertShowtime(showTime.getShowTime?.time).time}</p>
                    </div>
                    <hr/>
                    <div className="bottom">
                        <h4>{showTime.getShowTime?.movie.title} /</h4>
                        <span>{showTime.getShowTime?.movie.genre}</span>
                    </div>
                </div>
                <div className="down">
                    <div className="seatsBox">
                        <div className="seats">
                            <hr className="screen" />
                            <p className="screenP">Screen</p>
                            {/* {Object.keys(rows).map((rowKey) => (
                                <div className="row" key={`row-${rowKey}`}>
                                     Generowanie siedzeń w każdym rzędzie
                                    {rows[rowKey].map((seat) => (
                                        <div
                                            key={`seat-${seat.seatNumber}`}
                                            className={`seat ${seat.reserved ? 'occupied' : ''}`}
                                        >
                                            {seat.seatNumber}
                                        </div>
                                    ))}
                                </div>
                            ))}*/}
                            <div className="row">
                                <p>I</p>
                                <i><PiArmchairBold/></i>
                                <i><PiArmchairBold/></i>
                                <i><PiArmchairBold/></i>
                                <i><PiArmchairBold/></i>
                                <i><PiArmchairBold/></i>
                                <i><PiArmchairBold/></i>
                                <p>I</p>
                            </div>
                            <div className="row">
                                <p>II</p>
                                <i><PiArmchairBold/></i>
                                <i><PiArmchairBold/></i>
                                <i><PiArmchairBold/></i>
                                <i><PiArmchairBold/></i>
                                <i><PiArmchairBold/></i>
                                <i><PiArmchairBold/></i>
                                <p>II</p>
                            </div>
                            <div className="row">
                                <p>III</p>
                                <i><PiArmchairBold/></i>
                                <i><PiArmchairBold/></i>
                                <i><PiArmchairBold/></i>
                                <i><PiArmchairBold/></i>
                                <i><PiArmchairBold/></i>
                                <i><PiArmchairBold/></i>
                                <p>III</p>
                            </div>
                            <div className="row">
                                <p>IV</p>
                                <i><PiArmchairBold/></i>
                                <i><PiArmchairBold/></i>
                                <i><PiArmchairBold/></i>
                                <i><PiArmchairBold/></i>
                                <i><PiArmchairBold/></i>
                                <i><PiArmchairBold/></i>
                                <p>IV</p>
                            </div>
                            <div className="row">
                                <p>V</p>
                                <i><PiArmchairBold/></i>
                                <i><PiArmchairBold/></i>
                                <i><PiArmchairBold/></i>
                                <i><PiArmchairBold/></i>
                                <i><PiArmchairBold/></i>
                                <i><PiArmchairBold/></i>
                                <p>V</p>
                            </div>
                        </div>
                    </div>
                    <hr/>
                    <div className="legend">
                        <h6>Legend</h6>
                        <div className="signs">
                            <div className="colors">
                                <div className="color">
                                    <div className="dye"></div>
                                    <span>Free</span>
                                </div>
                                <div className="color">
                                    <div className="dye"></div>
                                    <span>Busy</span>
                                </div>
                                <div className="color">
                                    <div className="dye"></div>
                                    <span>Pick</span>
                                </div>
                            </div>
                            <div className="icon">
                                <i><PiArmchairBold/></i>
                                <span>Armchair</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
    }

                export {SeatsView};
