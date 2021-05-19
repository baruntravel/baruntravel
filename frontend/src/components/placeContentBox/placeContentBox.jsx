import React, { useEffect, useRef } from "react";
import SortBox from "../../components/common/sortBox/sortBox";

import styles from "./placeContentBox.module.css";

const PlaceContentBox = () => {
  // places : place id, place images
  const imageBoxRef = useRef();
  const images = [
    {
      id: 1,
      url: "https://i.pinimg.com/564x/d7/ec/75/d7ec75c9e68873ee75b734ac4ab09ced.jpg",
    },
    {
      id: 2,
      url: "https://i.pinimg.com/474x/30/5a/21/305a216481dfaaec10fd59cf1f667652.jpg",
    },
    {
      id: 3,
      url: "https://i.pinimg.com/474x/5f/a2/8e/5fa28eae2bdebd6ab2d30690304927b9.jpg",
    },
    {
      id: 4,
      url: "https://i.pinimg.com/474x/a6/56/70/a65670944d4bf492a3a71c4a95bb3910.jpg",
    },
    {
      id: 5,
      url: "https://i.pinimg.com/474x/e8/58/f3/e858f330363c0fb4240ca8cad087f74d.jpg",
    },
    {
      id: 6,
      url: "https://i.pinimg.com/474x/c5/eb/47/c5eb47f58dd27a764f88551151f54893.jpg",
    },
    {
      id: 3,
      url: "https://i.pinimg.com/474x/5f/a2/8e/5fa28eae2bdebd6ab2d30690304927b9.jpg",
    },
    {
      id: 4,
      url: "https://i.pinimg.com/474x/a6/56/70/a65670944d4bf492a3a71c4a95bb3910.jpg",
    },
    {
      id: 5,
      url: "https://i.pinimg.com/474x/e8/58/f3/e858f330363c0fb4240ca8cad087f74d.jpg",
    },
    {
      id: 6,
      url: "https://i.pinimg.com/474x/c5/eb/47/c5eb47f58dd27a764f88551151f54893.jpg",
    },
    {
      id: 1,
      url: "https://i.pinimg.com/564x/d7/ec/75/d7ec75c9e68873ee75b734ac4ab09ced.jpg",
    },
    {
      id: 2,
      url: "https://i.pinimg.com/474x/30/5a/21/305a216481dfaaec10fd59cf1f667652.jpg",
    },
    {
      id: 3,
      url: "https://i.pinimg.com/474x/5f/a2/8e/5fa28eae2bdebd6ab2d30690304927b9.jpg",
    },
    {
      id: 4,
      url: "https://i.pinimg.com/474x/a6/56/70/a65670944d4bf492a3a71c4a95bb3910.jpg",
    },
    {
      id: 3,
      url: "https://i.pinimg.com/474x/5f/a2/8e/5fa28eae2bdebd6ab2d30690304927b9.jpg",
    },
    {
      id: 4,
      url: "https://i.pinimg.com/474x/a6/56/70/a65670944d4bf492a3a71c4a95bb3910.jpg",
    },
    {
      id: 5,
      url: "https://i.pinimg.com/474x/e8/58/f3/e858f330363c0fb4240ca8cad087f74d.jpg",
    },
    {
      id: 6,
      url: "https://i.pinimg.com/474x/c5/eb/47/c5eb47f58dd27a764f88551151f54893.jpg",
    },
    {
      id: 7,
      url: "https://i.pinimg.com/474x/f9/8e/4d/f98e4d66f6f75e2aa312833751554411.jpg",
    },
    {
      id: 8,
      url: "https://i.pinimg.com/474x/7a/27/8f/7a278fcb2702fcaeadbf46f7ac1722d3.jpg",
    },
    {
      id: 9,
      url: "https://i.pinimg.com/474x/18/07/90/180790ed94257c237355a7187580448d.jpg",
    },
  ];
  useEffect(() => {
    const image__Elements = Array.from(imageBoxRef.current.children).map(
      (imageContainer) => imageContainer.firstChild
    ); // 이미지 요소들 모두 뽑아오기
    if ("IntersectionObserver" in window) {
      let lazyImageObserver = new IntersectionObserver(
        function (entries, observer) {
          entries.forEach(function (entry) {
            if (entry.isIntersecting) {
              let lazyImage = entry.target;
              console.log(lazyImage);
              lazyImage.src = lazyImage.dataset.src;
              lazyImageObserver.unobserve(lazyImage);
            }
          });
        },
        { threshold: 0.1 }
      );

      image__Elements.forEach(function (lazyImage) {
        lazyImageObserver.observe(lazyImage);
      });
    }
  }, []);
  return (
    <div className={styles.PlaceContentBox}>
      {/* <div className="functionBox"> */}
      <div className={styles.functionBox}>
        <span>아이콘 2개</span>
        <SortBox />
      </div>
      <div ref={imageBoxRef} className={styles.imageBox}>
        {images.map((item, index) => (
          <div key={index} className={styles.imageContainer}>
            <img className={styles.img} data-src={item.url} alt="" />
          </div>
        ))}
      </div>
    </div>
  );
};

export default PlaceContentBox;
