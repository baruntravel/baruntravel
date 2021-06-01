import styles from "./routeMap.module.css";
import { useState, useEffect } from "react";
import { useHistory } from "react-router-dom";
import { getRoutesByRange } from "../../../api/routeAPI";

const RouteMap = ({ places, mapHandler, routesHandler, routes }) => {
  const { kakao } = window;
  const [map, setMap] = useState();
  const [markers, setMarkers] = useState([]);
  const infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });
  let history = useHistory();

  //map init
  useEffect(() => {
    initMap();

    function initMap() {
      document.querySelector("body").style.overflow = "hidden"; // mobile에서 스크롤 막음
      let x = Object.values(routes)[0].places[0].x,
        y = Object.values(routes)[0].places[0].y;
      let container = document.getElementById("Map");
      const options = {
        center: new kakao.maps.LatLng(y, x),
        level: 4,
      };
      let tmpMap = new kakao.maps.Map(container, options);

      mapHandler(tmpMap);
      boundsHandler(tmpMap);
      setMap(tmpMap);
    }

    function boundsHandler(map) {
      kakao.maps.event.addListener(map, "dragend", async function () {
        let bounds = map.getBounds();
        let ne = bounds.getNorthEast();
        let sw = bounds.getSouthWest();
        const { content } = await getRoutesByRange(ne, sw);
        console.log(content);
        // routesHandler(content);
      });
    }
  }, []);

  //after map rendered
  useEffect(() => {
    displayMarker(infowindow);

    if (places[0]) {
      moveToRoute(places[0]);
    }

    let path = addPath();
    return () => removePath(path); //Unmount 직전에 path 지움

    function displayMarker(infowindow) {
      removeMarker();
      let bounds = new kakao.maps.LatLngBounds();
      for (let i = 0; i < places.length; i++) {
        let placePosition = new kakao.maps.LatLng(places[i].y, places[i].x);
        let marker = addMarker(placePosition, i);
        setMarkers((markers) => [...markers, marker]);
        bounds.extend(placePosition);
        (function (marker, title, id) {
          // kakao.maps.event.addListener(marker, "mouseover", function () {
          //   displayInfowindow(marker, title, id);
          // });
          kakao.maps.event.addListener(marker, "click", function () {
            displayInfowindow(marker, title, id);
          });

          // kakao.maps.event.addListener(marker, "mouseout", function () {
          //   infowindow.close();
          // });
        })(marker, places[i].placeName, places[i].id);
      }
    }

    function addPath() {
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

    function removePath(path) {
      path.setMap(null);
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

    function displayInfowindow(marker, title, id) {
      var content = `
      <div class="infowindow" style="padding: 10px; z-index:1;">
        ${title}
      </div>`;
      infowindow.setContent(content);
      infowindow.open(map, marker);
      document.querySelectorAll(".infowindow").forEach((item) => {
        item.addEventListener("click", () => {
          history.push(`/place/${id}`);
        });
      });
    }

    function moveToRoute(dest) {
      let moveLatLng = new kakao.maps.LatLng(dest.y, dest.x);
      map.panTo(moveLatLng);
    }
  }, [places]);

  return <div className={styles.map} id="Map" />;
};

export default RouteMap;
