import React from "react";
import SortBox from "../common/sortBox/sortBox";
import styles from "./placeContentBox.module.css";

const PlaceContentBox = ({ places }) => {
  // places : place id, place images
  const images = [
    {
      id: 1,
      url:
        "https://i.pinimg.com/564x/d7/ec/75/d7ec75c9e68873ee75b734ac4ab09ced.jpg",
    },
    {
      id: 2,
      url:
        "https://i.pinimg.com/474x/30/5a/21/305a216481dfaaec10fd59cf1f667652.jpg",
    },
    {
      id: 3,
      url:
        "https://i.pinimg.com/474x/5f/a2/8e/5fa28eae2bdebd6ab2d30690304927b9.jpg",
    },
    {
      id: 4,
      url:
        "https://i.pinimg.com/474x/a6/56/70/a65670944d4bf492a3a71c4a95bb3910.jpg",
    },
    {
      id: 5,
      url:
        "https://i.pinimg.com/474x/e8/58/f3/e858f330363c0fb4240ca8cad087f74d.jpg",
    },
    {
      id: 6,
      url:
        "https://i.pinimg.com/474x/c5/eb/47/c5eb47f58dd27a764f88551151f54893.jpg",
    },
    {
      id: 3,
      url:
        "https://i.pinimg.com/474x/5f/a2/8e/5fa28eae2bdebd6ab2d30690304927b9.jpg",
    },
    {
      id: 4,
      url:
        "https://i.pinimg.com/474x/a6/56/70/a65670944d4bf492a3a71c4a95bb3910.jpg",
    },
    {
      id: 5,
      url:
        "https://i.pinimg.com/474x/e8/58/f3/e858f330363c0fb4240ca8cad087f74d.jpg",
    },
    {
      id: 6,
      url:
        "https://i.pinimg.com/474x/c5/eb/47/c5eb47f58dd27a764f88551151f54893.jpg",
    },
    {
      id: 1,
      url:
        "https://i.pinimg.com/564x/d7/ec/75/d7ec75c9e68873ee75b734ac4ab09ced.jpg",
    },
    {
      id: 2,
      url:
        "https://i.pinimg.com/474x/30/5a/21/305a216481dfaaec10fd59cf1f667652.jpg",
    },
    {
      id: 3,
      url:
        "https://i.pinimg.com/474x/5f/a2/8e/5fa28eae2bdebd6ab2d30690304927b9.jpg",
    },
    {
      id: 4,
      url:
        "https://i.pinimg.com/474x/a6/56/70/a65670944d4bf492a3a71c4a95bb3910.jpg",
    },
  ];
  return (
    <div className={styles.PlaceContentBox}>
      {/* <div className="functionBox"> */}
      <div className={styles.functionBox}>
        <span>아이콘 2개</span>
        <SortBox />
      </div>
      <div className={styles.imageBox}>
        {images.map((item, index) => (
          <div key={index} className={styles.imageContainer}>
            {/* <img data-link={ite} */}
            <img className={styles.img} src={item.url} alt="장소 사진" />
          </div>
        ))}
      </div>
    </div>
  );
};

export default PlaceContentBox;
