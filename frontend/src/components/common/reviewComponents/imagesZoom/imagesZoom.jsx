import { Carousel } from "antd";
import React, { useRef } from "react";
import styles from "./imagesZoom.module.css";
import {
  CloseOutlined,
  LeftCircleOutlined,
  RightCircleOutlined,
} from "@ant-design/icons";

const ImagesZoom = ({ images, onClose, index }) => {
  const arrowRef = useRef();
  const onClickPrev = () => {
    arrowRef.current.prev();
  };
  const onClickNext = () => {
    arrowRef.current.next();
  };
  return (
    <div className={styles.ImagesZoom}>
      <div className={styles.container}>
        <header className={styles.header}>
          <button className={styles.closeBtn} onClick={onClose}>
            <CloseOutlined style={{ color: "white" }} />
          </button>
        </header>
        <div className={styles.carousel}>
          <Carousel
            ref={arrowRef}
            className={styles.carousel__Body}
            dots={false}
            initialSlide={index || 0}
          >
            {images.map((v, index) => (
              <div className={styles.imgContainer} key={index}>
                <img
                  key={index}
                  className={styles.img}
                  src={v}
                  alt={"img uploaded"}
                />
              </div>
            ))}
          </Carousel>
        </div>
        <div className={styles.btnBox}>
          <button className={styles.prevBtn} onClick={onClickPrev}>
            <LeftCircleOutlined style={{ color: "white" }} />
          </button>
          <button className={styles.nextBtn} onClick={onClickNext}>
            <RightCircleOutlined style={{ color: "white" }} />
          </button>
        </div>
      </div>
    </div>
  );
};
export default ImagesZoom;
