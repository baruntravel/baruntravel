import React, { useEffect } from "react";
import styles from "./imageMap.module.css";
const { kakao } = window;

const ImageMap = (props) => {
  useEffect(() => {
    let markerPosition = new kakao.maps.LatLng(33.450701, 126.570667);
    let marker = {
      position: markerPosition,
    };
    let staticMapContainer = document.getElementById("staticMap"),
      staticMapOption = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 4,
        marker: marker,
      };
    let staticMap = new kakao.maps.StaticMap(
      staticMapContainer,
      staticMapOption
    );
  }, []);
  return (
    <div className={styles.ImageMap}>
      <div id="staticMap" className={styles.map} />
    </div>
  );
};

export default ImageMap;
