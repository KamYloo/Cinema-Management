import React, { useState } from "react";
import { useNavigate } from 'react-router-dom'
import "../../styles/movieDetail.css";

function MovieDetail() {
    const navigate = useNavigate();

    return (
        <div className="movieDetail">
            <div className="backGround"></div>
            <div className="center">
                <img src="https://m.media-amazon.com/images/M/MV5BYjY2NDZhN2EtZmYwNS00NDU2LThiODAtNmY4OWY2ODNmZWRmXkEyXkFqcGc@._V1_.jpg" alt="" />
                <div className="data">
                    <h3>fiLmdsfdsivjos</h3>
                </div>
            </div>
        </div>
    );
}

export { MovieDetail };
