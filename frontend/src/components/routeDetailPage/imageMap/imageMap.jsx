import React, { useEffect, useState } from "react";
import styles from "./imageMap.module.css";
const { kakao } = window;

const ImageMap = ({ places, centerX, centerY }) => {
  const [staticMap, setStaticMap] = useState();
  const [markers, setMarkers] = useState([]);

  useEffect(() => {
    function initMap() {
      // 초기 지도 생성 시 어느 지역의 지도를 보여줄 것인가?
      let staticMapContainer = document.getElementById("staticMap"),
        staticMapOption = {
          center: new kakao.maps.LatLng(centerY || 37.566826, centerX || 126.9786567),
          level: 4,
        };
      let map = new kakao.maps.Map(staticMapContainer, staticMapOption);
      setStaticMap(map);
    }

    initMap();
  }, []);

  useEffect(() => {
    function moveLocationMap() {
      staticMap.setCenter(new kakao.maps.LatLng(centerY, centerX));
    }
    function addMarker(position, index, map) {
      var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new kakao.maps.Size(36, 37), // 마커 이미지의 크기
        imgOptions = {
          spriteSize: new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
          spriteOrigin: new kakao.maps.Point(0, index * 46 + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
          offset: new kakao.maps.Point(13, 37), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
        marker = new kakao.maps.Marker({
          position: position, // 마커의 위치
          image: markerImage,
        });

      marker.setMap(map); // 지도 위에 마커를 표출합니다
      setMarkers((markers) => [...markers, marker]);

      return marker;
    }
    function displayMarker(map) {
      removeMarker();
      let bounds = new kakao.maps.LatLngBounds();
      for (let i = 0; i < places.length; i++) {
        let placePosition = new kakao.maps.LatLng(places[i].y, places[i].x);
        let marker = addMarker(placePosition, i, map);
        bounds.extend(placePosition);
      }
      map.setBounds(bounds);
    }

    function addPath(map) {
      let linepath = [];
      for (let i = 0; i < places.length; i++) {
        linepath.push(new kakao.maps.LatLng(places[i].y, places[i].x));
      }

      let polyline = new kakao.maps.Polyline({
        path: linepath, // 선을 구성하는 좌표배열 입니다
        strokeWeight: 5, // 선의 두께 입니다
        strokeColor: "#1890ff", // 선의 색깔입니다
        strokeOpacity: 1, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
        strokeStyle: "solid", // 선의 스타일입니다
      });
      polyline.setMap(map);
      return polyline;
    }

    function removeMarker() {
      for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
      }
      setMarkers([]);
    }

    function removePath(path) {
      path && path.setMap(null);
    }

    let path;
    if (centerX && centerY && places) {
      moveLocationMap();
      displayMarker(staticMap);
      path = addPath(staticMap);
    }
    return () => removePath(path);
  }, [places, centerX, centerY]);
  return (
    <div className={styles.ImageMap}>
      <div id="staticMap" className={styles.map} />
    </div>
  );
};

export default ImageMap;
