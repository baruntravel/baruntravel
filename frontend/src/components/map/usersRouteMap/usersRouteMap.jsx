import styles from "./usersRouteMap.module.css";
import { useState, useEffect, useCallback } from "react";

const UsersRouteMap = ({ places }) => {
  const { kakao } = window;
  const [map, setMap] = useState();
  const [markers, setMarkers] = useState([]);

  const infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });

  //map init
  useEffect(() => {
    initMap();
  }, []);

  function initMap() {
    document.querySelector("body").style.overflow = "hidden"; // mobile에서 스크롤 막음
    let container = document.getElementById("Map");
    const options = {
      // center: center,
      center: new kakao.maps.LatLng(37.566826, 126.9786567),
      level: 4,
    };
    let tmpMap = new kakao.maps.Map(container, options);
    getBoundsHandler(tmpMap);
    setMap(tmpMap);
  }

  const getBoundsHandler = (map) => {
    kakao.maps.event.addListener(map, "bounds_changed", function () {
      var bounds = map.getBounds();
      var swLatlng = bounds.getSouthWest();
      var neLatlng = bounds.getNorthEast();
      console.log(swLatlng, neLatlng);
    });
  };

  //루트 바뀔 때 마다
  useEffect(() => {
    displayMarker(infowindow);
  }, [places]);

  function displayMarker(infowindow) {
    removeMarker();
    let bounds = new kakao.maps.LatLngBounds();
    for (let i = 0; i < places.length; i++) {
      let placePosition = new kakao.maps.LatLng(places[i].y, places[i].x);
      let marker = addMarker(placePosition, i);
      setMarkers((markers) => [...markers, marker]);
      bounds.extend(placePosition);
      (function (marker, title) {
        kakao.maps.event.addListener(marker, "mouseover", function () {
          displayInfowindow(marker, title);
        });

        kakao.maps.event.addListener(marker, "mouseout", function () {
          infowindow.close();
        });
      })(marker, places[i].placeName);
    }
  }

  function addMarker(position, index) {
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

  function removeMarker() {
    for (var i = 0; i < markers.length; i++) {
      markers[i].setMap(null);
    }
    setMarkers([]);
  }

  function displayInfowindow(marker, title) {
    var content = '<div style="padding:5px;z-index:1;">' + title + "</div>";
    infowindow.setContent(content);
    infowindow.open(map, marker);
  }

  function getScreenXY() {
    console.log("Drag Event Done");
    let latlng = map.getCenter();
    console.log(latlng.getLat(), latlng.getLng());
  }

  return <div className={styles.map} id="Map" />;
};

export default UsersRouteMap;
