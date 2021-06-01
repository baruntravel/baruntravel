import React, { useCallback, useEffect, useRef } from "react";
import { useHistory } from "react-router-dom";
import SortBox from "../../common/sortBox/sortBox";
import styles from "./placeContentBox.module.css";

const PlaceContentBox = () => {
  // places : place id, place images
  const history = useHistory();
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

  const onClickKakaoMap = useCallback(() => {
    history.push("/kakaoPlace"); // state와 같이 보내면된다.
  }, [history]);
  const onClickImage = useCallback(
    (e) => {
      // e.target // target 자식 요소의 key를 id로 념겨준다
      // 테스트용도는 review 1로 그냥 해버림
      history.push("/review/detail/1");
    },
    [history]
  );
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
              lazyImage.classList.remove(styles["lazy"]);
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
        <div>
          <span
            onClick={() => {
              history.push("/routePickPlace");
            }}
          >
            임시 아이콘
          </span>
          <img
            src="https://i.pinimg.com/474x/ca/e2/e6/cae2e62f4c5141349e5c9b4d620dbf1e.jpg"
            style={{ width: "50px" }}
            onClick={onClickKakaoMap}
          />
        </div>
        <SortBox />
      </div>
      <div ref={imageBoxRef} className={styles.imageBox} onClick={onClickImage}>
        {images.map(
          (
            item,
            index // 이미지를 받아오면 해당 이미지의 id를 key로 넣고 그 key를 이용해서 리뷰 창 띄워주기
          ) => (
            <div key={index} className={styles.imageContainer}>
              <img
                className={`${styles.img} ${styles.lazy}`}
                data-src={item.url}
                alt=""
              />
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default PlaceContentBox;
