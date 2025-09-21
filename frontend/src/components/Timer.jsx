import React, { useState, useEffect, useRef } from "react";

export default function Timer() {
  const [time, setTime] = useState(20 * 60); // 20 minutes in seconds
  const [isActive, setIsActive] = useState(false);
  const intervalRef = useRef(null);

  useEffect(() => {
    if (isActive) {
      intervalRef.current = setInterval(() => {
        setTime((prev) => (prev > 0 ? prev - 1 : 0));
      }, 1000);
    } else {
      clearInterval(intervalRef.current);
    }

    return () => clearInterval(intervalRef.current);
  }, [isActive]);

  const togglePause = () => {
    setIsActive((prev) => !prev);
  };

  const resetTimer = () => {
    setTime(20 * 60);
    setIsActive(false);
  };

  const formatTime = (seconds) => {
    const m = Math.floor(seconds / 60);
    const s = seconds % 60;
    return `${m.toString().padStart(2, "0")}:${s
      .toString()
      .padStart(2, "0")}`;
  };

  return (
    <div className="flex flex-col items-center gap-3 p-4">
      <h1 className="text-3xl font-bold">{formatTime(time)}</h1>
      <div className="flex gap-2">
        <button
          onClick={togglePause}
          className="px-4 py-2 bg-blue-500 text-white rounded-lg shadow"
        >
          {isActive ? "Pause" : "Resume"}
        </button>
        <button
          onClick={resetTimer}
          className="px-4 py-2 bg-red-500 text-white rounded-lg shadow"
        >
          Reset
        </button>
      </div>
    </div>
  );
}
