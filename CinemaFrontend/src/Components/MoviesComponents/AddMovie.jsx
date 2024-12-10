import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "../../styles/form.css";
import { useDispatch } from "react-redux";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import {createMovieAction, uploadMoviePhotoAction} from "../../Redux/MovieService/Action";
import { format } from "date-fns";
import toast from "react-hot-toast";

function AddMovie() {
    const [formState, setFormState] = useState({
        title: "",
        description: "",
        genre: "",
        duration: 0,
        imageFile: null,
        showtimes: [],
        dateList: [],
    });

    const [preview, setPreview] = useState("");
    const [dateTime, setDateTime] = useState(null);
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormState((prev) => ({ ...prev, [name]: value }));
    };

    const handleFileChange = (e) => {
        const file = e.target.files[0];
        setFormState((prev) => ({ ...prev, imageFile: file }));
    };

    const handleAddShowtime = () => {
        if (dateTime) {
            const formattedDate = format(dateTime, "yyyy-MM-dd'T'HH:mm:ss");
            setFormState((prev) => ({
                ...prev,
                showtimes: [
                    ...prev.showtimes,
                    {
                        date: dateTime.toLocaleDateString(),
                        time: dateTime.toLocaleTimeString([], {
                            hour: "2-digit",
                            minute: "2-digit",
                        }),
                    },
                ],
                dateList: [...prev.dateList, { time: formattedDate }],
            }));
            setDateTime(null);
        }
    };

    const handleRemoveShowtime = (index) => {
        setFormState((prev) => ({
            ...prev,
            showtimes: prev.showtimes.filter((_, i) => i !== index),
            dateList: prev.dateList.filter((_, i) => i !== index),
        }));
    };

    const handleFormSubmit = async (e) => {
        e.preventDefault();

        if (!formState.imageFile) {
            toast.error("Please upload an image before submitting the movie.");
            return;
        }

        const formData = new FormData();
        formData.append("file", formState.imageFile);

        let uploadedPhotoPath;
        try {
            uploadedPhotoPath = await dispatch(uploadMoviePhotoAction(formData));
            console.log("fdssdfs"+uploadedPhotoPath);
        } catch (uploadError) {
            console.error("Błąd przesyłania zdjęcia:", uploadError);
            toast.error("Failed to upload the image. Please try again.");
            return;
        }

        const movieData = {
            title: formState.title,
            description: formState.description,
            image: uploadedPhotoPath,
            genre: formState.genre,
            duration: formState.duration,
            showTimes: formState.dateList,
        };

        try {
            await dispatch(createMovieAction(movieData));
            toast.success("MovieService added successfully!");
            navigate("/movies");
        } catch (error) {
            console.error("Błąd przesyłania zdjęcia:", error);
            toast.error("Failed to add movie. Please try again.");
        } finally {
            setFormState({
                title: "",
                description: "",
                genre: "",
                duration: 0,
                imageFile: null,
                showtimes: [],
                dateList: [],
            });
        }
    };

    useEffect(() => {
        if (formState.imageFile) {
            const previewUrl = URL.createObjectURL(formState.imageFile);
            setPreview(previewUrl);
            return () => URL.revokeObjectURL(previewUrl);
        }
    }, [formState.imageFile]);

    return (
        <div className="addMovie">
            <div className="box">
                <div className="title">
                    <h2>Add Movie</h2>
                </div>
                <form onSubmit={handleFormSubmit}>
                    <div className="editPic">
                        <div className="left">
                            <img className="trackImg" src={preview} alt="" />
                            <span>{formState.imageFile?.name}</span>
                        </div>
                        <div className="right">
                            <input type="file" onChange={handleFileChange} />
                            <button
                                className="formBtn"
                                type="button"
                                onClick={() => document.querySelector('input[type="file"]').click()}
                            >
                                Select Img
                            </button>
                        </div>
                    </div>
                    <div className="editShortText">
                        <h4>Title</h4>
                        <input
                            type="text"
                            name="title"
                            value={formState.title}
                            onChange={handleInputChange}
                            required
                        />
                    </div>
                    <div className="editLongText">
                        <h4>Description</h4>
                        <textarea
                            name="description"
                            value={formState.description}
                            onChange={handleInputChange}
                            required
                        />
                    </div>
                    <div className="double">
                        <div className="editShortText">
                            <h4>Duration</h4>
                            <input
                                type="number"
                                name="duration"
                                value={formState.duration}
                                onChange={handleInputChange}
                                required
                            />
                        </div>
                        <div className="editShortText">
                            <h4>Genre</h4>
                            <select
                                name="genre"
                                value={formState.genre}
                                onChange={handleInputChange}
                                required
                            >
                                <option value="">Select Genre</option>
                                <option value="Animated">Animated</option>
                                <option value="Documentary">Documentary</option>
                                <option value="Horror">Horror</option>
                                <option value="Fiction">Fiction</option>
                                <option value="Comedy">Comedy</option>
                                <option value="Drama">Drama</option>
                                <option value="Action">Action</option>
                                <option value="War">War</option>
                                <option value="Crime">Crime</option>
                                <option value="Historical">Historical</option>
                                <option value="Adventure">Adventure</option>
                            </select>
                        </div>
                    </div>
                    <div className="editShortText">
                        <h4>Showtimes</h4>
                        <div className="chooseDate">
                            <DatePicker
                                selected={dateTime}
                                onChange={(selectedDate) => setDateTime(selectedDate)}
                                showTimeSelect
                                timeFormat="HH:mm"
                                timeIntervals={15}
                                dateFormat="dd/MM/yyyy h:mm aa"
                                timeCaption="Time"
                                placeholderText="Select date and time"
                            />
                            <button className="formBtn" type="button" onClick={handleAddShowtime}>
                                Add Showtime
                            </button>
                        </div>
                        <div className="showtimeList">
                            {formState.showtimes.map((showtime, index) => (
                                <div key={index} className="showtimeItem">
                                    <span>
                                        {showtime.date} {showtime.time}
                                    </span>
                                    <button
                                        className="formBtn"
                                        type="button"
                                        onClick={() => handleRemoveShowtime(index)}
                                    >
                                        Delete
                                    </button>
                                </div>
                            ))}
                        </div>
                    </div>
                    <div className="buttons">
                        <button className="formBtn" onClick={() => navigate("/movies")}>
                            Cancel
                        </button>
                        <button className="formBtn" type="submit">
                            Send
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
}

export { AddMovie };
