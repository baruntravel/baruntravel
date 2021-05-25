import React, { useEffect, useState } from "react";
import styles from "./ourPlaceMap.module.css";

const { kakao } = window;

const OurPlaceMap = ({ places }) => {
  const [map, setMap] = useState();
  const [currCategory, setCurrCategory] = useState(null);
  const [markers, setMarkers] = useState([]);

  useEffect(() => {
    function boundsHandler(map) {
      kakao.maps.event.addListener(map, "dragend", async function () {
        let bounds = map.getBounds();
        let swLatlng = bounds.getSouthWest();
        let neLatlng = bounds.getNorthEast();
        // const tmpRoutes = await getRoutesByRange(swLatlng, neLatlng);
        // routesHandler(tmpRoutes);
      });
    }
    function initMap() {
      document.querySelector("body").style.overflow = "hidden"; // mobile에서 스크롤 막음
      let x = 126.9786567, // places[0].x
        y = 37.566826; // places[0].y
      let container = document.getElementById("Map");
      const options = {
        center: new kakao.maps.LatLng(y, x),
        level: 4,
      };
      let tmpMap = new kakao.maps.Map(container, options);
      boundsHandler(tmpMap);
      setMap(tmpMap);
    }

    // 각 카테고리에 클릭 이벤트를 등록합니다
    function addCategoryClickEvent() {
      let category = document.getElementById("category");
      let children = category.children;

      for (var i = 0; i < children.length; i++) {
        children[i].onclick = onClickCategory;
      }
    }

    // 카테고리를 클릭했을 때 호출되는 함수입니다
    function onClickCategory() {
      let id = this.id;
      setCurrCategory(id);
      // searchPlaces(); 카테고리 검색 시 함수를 넣어줘야한다.
    }
    initMap();
    addCategoryClickEvent();
  }, []);

  useEffect(() => {
    // 현 지도 내에서 검색 구현, 카테고리 전체 추가

    function markerClickEvent(marker, place, index) {
      kakao.maps.event.addListener(marker, "click", function () {
        // updateClickedPlace(place); 마커가 클릭됐을 때 캐로셀 이동되게
        // clickedMarker(index);
      });
    }

    function displayMarker() {
      removeMarker();
      // let bounds = new kakao.maps.LatLngBounds();
      for (let i = 0; i < places.length; i++) {
        let placePosition = new kakao.maps.LatLng(places[i].y, places[i].x);
        let marker = addMarker(placePosition, i);
        setMarkers((markers) => [...markers, marker]);
        // bounds.extend(placePosition);
        markerClickEvent(marker, places[i], i);
      }
    }

    function addMarker(position, index) {
      let imageSrc =
          "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new kakao.maps.Size(36, 37), // 마커 이미지의 크기
        imgOptions = {
          spriteSize: new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
          spriteOrigin: new kakao.maps.Point(0, index * 46 + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
          offset: new kakao.maps.Point(13, 37), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
        markerImage = new kakao.maps.MarkerImage(
          imageSrc,
          imageSize,
          imgOptions
        ),
        marker = new kakao.maps.Marker({
          position: position, // 마커의 위치
          image: markerImage,
        });

      marker.setMap(map); // 지도 위에 마커를 표출합니다
      setMarkers((markers) => [...markers, marker]);
      return marker;
    }
    // 지도 위에 표시되고 있는 마커를 모두 제거합니다
    function removeMarker() {
      for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
      }
      setMarkers([]);
    }
  }, [places]);

  return <div className={styles.map} id="Map" />;
};

export default OurPlaceMap;
