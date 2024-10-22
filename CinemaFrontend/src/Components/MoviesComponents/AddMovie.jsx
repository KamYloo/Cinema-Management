import React, {useEffect, useState} from "react";
import { useNavigate } from 'react-router-dom'
import "../../styles/form.css";
import {useDispatch} from "react-redux";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

function AddMovie() {
    const [title, setTitle] = useState()
    const [movieImg, setMovieImg] = useState(null)
    const [description, setDescription] = useState()
    const [dateTime, setDateTime] = useState(null);  // Updated to hold both Date and Time
    const [showtimes, setShowtimes] = useState([]);
    const [preview, setPreview] = useState('');
    const dispatch = useDispatch()
    const navigate = useNavigate();

    const handleFileChange = (e) => {
        setMovieImg(e.target.files[0])
    };

    const handleAddShowtime = () => {
        if (dateTime) {
            const formattedDate = dateTime.toLocaleDateString();
            const formattedTime = dateTime.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
            setShowtimes([...showtimes, { date: formattedDate, time: formattedTime }]);
            setDateTime(null);  // Reset dateTime field after adding
        }
    };

    const handleRemoveShowtime = (index) => {
        setShowtimes(showtimes.filter((_, i) => i !== index));  // Remove the selected showtime
    };

    const addMovieHandler = () => {
        const formData = new FormData()
        formData.append('file', movieImg)
        formData.append('title', title)
        //dispatch(createAlbum(formData))
        setMovieImg(null)
    }

    useEffect(() => {
        if (movieImg) {
            const previewUrl = URL.createObjectURL(movieImg)
            setPreview(previewUrl)
            return () => {
                URL.revokeObjectURL(previewUrl)
            }
        }
    }, [movieImg])

    return (
        <div className="addMovie">
            <div className="box">
            <div className="title">
                <h2>Add Movie</h2>
            </div>
                <form onSubmit={addMovieHandler}>
                    <div className="editPic">
                        <div className="left">
                            <img src={preview} alt=""/>
                            <span>{movieImg?.name}</span>
                        </div>
                        <div className="right">
                            <input type="file" onChange={handleFileChange}/>
                            <button type="button"
                                    onClick={() => document.querySelector('input[type="file"]').click()}>
                                Select Img
                            </button>
                        </div>
                    </div>
                    <div className="editShortText">
                        <h4>Title</h4>
                        <input type="text" value={title} onChange={(e) => setTitle(e.target.value)}></input>
                    </div>
                    <div className="editLongText">
                        <h4>Description</h4>
                        <textarea value={description} onChange={(e) => setDescription(e.target.value)}></textarea>
                    </div>
                    <div className="editShortText">
                        <h4>Duration</h4>
                        <input type="text" value={title} onChange={(e) => setTitle(e.target.value)}></input>
                    </div>
                    <div className="editShortText">
                        <h4>Genre</h4>
                        <select>
                            <option value="likes">Sort by likes</option>
                            <option value="date-asc">Sort by date (ascending)</option>
                            <option value="date-desc">Sort by date (descending)</option>
                        </select>
                    </div>

                    <div className="editShortText">
                        <h4>Showtimes</h4>
                        <div className="chooseDate">
                            <div className="datePicker">
                                <DatePicker
                                    selected={dateTime}
                                    onChange={(selectedDate) => setDateTime(selectedDate)}  // Handle date and time selection
                                    showTimeSelect  // Enable time picker
                                    timeFormat="HH:mm"
                                    timeIntervals={15}
                                    dateFormat="dd/MM/yyyy h:mm aa"
                                    timeCaption="Time"
                                    placeholderText="Select date and time"
                                />
                            </div>

                            <button type="button" onClick={handleAddShowtime}>Add Showtime</button>
                        </div>
                        <div className="showtimeList">
                            {showtimes.map((showtime, index) => (
                                <div key={index} className="showtimeItem">
                                    <span>{showtime.date} {showtime.time}</span>
                                    <button className="delete" type="button"
                                            onClick={() => handleRemoveShowtime(index)}>
                                        Delete
                                    </button>
                                </div>
                            ))}
                        </div>
                    </div>


                    <div className="buttons">
                        <button onClick={()=> navigate("/movies")} className='submit'>Cancel</button>
                        <button type="submit" className='submit'>Send</button>
                    </div>
                </form>
            </div>
        </div>
    );
}

export {AddMovie};



