import React, { useEffect } from "react";
import MapContainer from "../../../containers/mapContainer/mapContainer";
import styles from "./routeMap.module.css";
const { kakao } = window;

const RouteMap = ({ markers, width, height }) => {
  useEffect(() => {
    const dummy = markers.map((place) => {
      return [place.x, place.y];
    });
    let container = document.getElementById("Map");

    //Map의 width,height props 전달되면 해당 사이즈로 변경 / 아니면 기본 사이즈
    if (width) {
      container.style.width = width;
      container.style.height = height;
    }

    const options = {
      center: new kakao.maps.LatLng(37.566826, 126.9786567),
      level: 3,
    };
    const map = new kakao.maps.Map(container, options);
    // --
    const infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });

    var imageSrc =
      "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
    for (var i = 0; i < dummy.length; i++) {
      // 마커 이미지의 이미지 크기 입니다
      var imageSize = new kakao.maps.Size(24, 35);

      // 마커 이미지를 생성합니다
      var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

      // 마커를 생성합니다
      var marker = new kakao.maps.Marker({
        map: map, // 마커를 표시할 지도
        position: new kakao.maps.LatLng(dummy[i][1], dummy[i][0]), // 마커를 표시할 위치
        image: markerImage, // 마커 이미지
      });
    }
    const linePath = [];
    for (let i = 0; i < dummy.length; i++) {
      linePath.push(new kakao.maps.LatLng(dummy[i][1], dummy[i][0]));
    }
    var polyline = new kakao.maps.Polyline({
      path: linePath, // 선을 구성하는 좌표배열 입니다
      strokeWeight: 5, // 선의 두께 입니다
      strokeColor: "#FFAE00", // 선의 색깔입니다
      strokeOpacity: 0.7, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
      strokeStyle: "solid", // 선의 스타일입니다
    });

    // 지도에 선을 표시합니다
    polyline.setMap(map);
  }, [markers]);

  return (
    <div className={styles.RouteMap}>
      <MapContainer width={width} height={height} />
    </div>
  );
};

export default RouteMap;
