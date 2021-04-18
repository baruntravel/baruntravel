import React, { useCallback, useEffect, useRef, useState } from "react";
import styles from "./hotplaceMap.module.css";

const { kakao } = window;
const HotplaceMap = ({
  searchRef,
  inputRef,
  updateClickedPlace,
  updateSearchPlaces,
  place,
  markerIndex,
  clickedMarker,
}) => {
  const [mapHooks, setMapHooks] = useState(null);
  const [markersHooks, setMarkersHooks] = useState();

  // 엘리먼트에 이벤트 핸들러를 등록하는 함수입니다
  const addEventHandle = useCallback((target, type, callback) => {
    if (target.addEventListener) {
      target.addEventListener(type, callback);
    } else {
      target.attachEvent("on" + type, callback);
    }
  }, []);
  useEffect(() => {
    if (mapHooks) {
      mapHooks.panTo(new kakao.maps.LatLng(place.y, place.x));
      // markersHooks[markerIndex].T.Xj =
      markersHooks[markerIndex].setOpacity(0.5);
    }
  }, [place]);

  useEffect(() => {
    const placeOverlay = new kakao.maps.CustomOverlay({ zIndex: 1 });
    const contentNode = document.createElement("div");
    contentNode.className = "placeinfo_wrap";
    let markers = [];
    let keyword;
    let currCategory = "";
    const mapContainer = document.getElementById("Map"), // 지도를 표시할 div
      mapOption = {
        center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
        level: 4, // 지도의 확대 레벨
      };
    const map = new kakao.maps.Map(mapContainer, mapOption);
    setMapHooks(map);
    const ps = new kakao.maps.services.Places(map);

    kakao.maps.event.addListener(map, "dragend", () => {
      searchPlaces();
    });
    kakao.maps.event.addListener(map, "dragend", () => {
      searchPlacesWithKeyword();
    });
    // 커스텀 오버레이의 컨텐츠 노드에 mousedown, touchstart 이벤트가 발생했을때
    // 지도 객체에 이벤트가 전달되지 않도록 이벤트 핸들러로 kakao.maps.event.preventMap 메소드를 등록합니다
    addEventHandle(contentNode, "mousedown", kakao.maps.event.preventMap);
    addEventHandle(contentNode, "touchstart", kakao.maps.event.preventMap);
    // 커스텀 오버레이 컨텐츠를 설정합니다
    placeOverlay.setContent(contentNode);
    searchRef.current.addEventListener("submit", addSubmitKeyword);

    // 카테고리 검색을 요청하는 함수입니다
    addCategoryClickEvent();
    searchPlacesWithKeyword();
    function addSubmitKeyword() {
      currCategory = "";
      keyword = inputRef.current.value;
      searchPlacesWithKeyword();
    }
    function placesSearchKeywordCB(data, status, pagination) {
      if (status === kakao.maps.services.Status.OK) {
        displayPlaces(data);
        updateSearchPlaces(data);
      } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
        alert("검색 결과가 존재하지 않습니다.");
        return;
      } else if (status === kakao.maps.services.Status.ERROR) {
        alert("검색 결과 중 오류가 발생했습니다.");
        return;
      }
    }
    function searchPlacesWithKeyword() {
      if (!currCategory && keyword) {
        //keyword로 검색했을 때,
        placeOverlay.setMap(null);
        removeMarker();
        ps.keywordSearch(keyword, placesSearchKeywordCB, {
          useMapBounds: true,
        });
      } else {
        return;
      }
    }
    function searchPlaces() {
      // 카테고리로 검색했을 때,
      if (!currCategory) {
        return;
      } else {
        // 커스텀 오버레이를 숨깁니다
        placeOverlay.setMap(null);
        // 지도에 표시되고 있는 마커를 제거합니다
        removeMarker();
        ps.categorySearch(currCategory, placesSearchCB, {
          useMapBounds: true,
        });
      }
    }

    // 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
    function placesSearchCB(data, status, pagination) {
      if (status === kakao.maps.services.Status.OK) {
        // 정상적으로 검색이 완료됐으면 지도에 마커를 표출합니다
        displayPlaces(data);
        updateSearchPlaces(data);
      } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
        // 검색결과가 없는경우 해야할 처리가 있다면 이곳에 작성해 주세요
      } else if (status === kakao.maps.services.Status.ERROR) {
        // 에러로 인해 검색결과가 나오지 않은 경우 해야할 처리가 있다면 이곳에 작성해 주세요
      }
    }
    function overlayClickEvent(marker, place, index) {
      kakao.maps.event.addListener(marker, "click", function () {
        updateClickedPlace(place);
        clickedMarker(index);
      });
    }
    // 지도에 마커를 표출하는 함수입니다
    function displayPlaces(places) {
      // 몇번째 카테고리가 선택되어 있는지 얻어옵니다
      // 이 순서는 스프라이트 이미지에서의 위치를 계산하는데 사용됩니다
      if (currCategory) {
        var order = document
          .getElementById(currCategory)
          .getAttribute("data-order");

        for (var i = 0; i < places.length; i++) {
          // 마커를 생성하고 지도에 표시합니다
          var marker = addMarker(
            new kakao.maps.LatLng(places[i].y, places[i].x),
            order
          );
          // 마커와 검색결과 항목을 클릭 했을 때
          // 장소정보를 표출하도록 클릭 이벤트를 등록합니다
          overlayClickEvent(marker, places[i], i);
        }
      } else if (keyword) {
        removeMarker();
        for (let i = 0; i < places.length; i++) {
          var placePosition = new kakao.maps.LatLng(places[i].y, places[i].x),
            marker = addMarker(placePosition, i);
          overlayClickEvent(marker, place[i], i);
        }
      }
    }

    // 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
    function addMarker(position, order) {
      if (currCategory) {
        var imageSrc =
            "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
          imageSize = new kakao.maps.Size(27, 28), // 마커 이미지의 크기
          imgOptions = {
            spriteSize: new kakao.maps.Size(72, 208), // 스프라이트 이미지의 크기
            spriteOrigin: new kakao.maps.Point(46, order * 36), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
            offset: new kakao.maps.Point(11, 28), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
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
        markers.push(marker); // 배열에 생성된 마커를 추가합니다
        setMarkersHooks(markers);
        return marker;
      } else {
        var imageSrc =
            "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
          imageSize = new kakao.maps.Size(36, 37), // 마커 이미지의 크기
          imgOptions = {
            spriteSize: new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
            spriteOrigin: new kakao.maps.Point(0, order * 46 + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
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
        markers.push(marker); // 배열에 생성된 마커를 추가합니다
        setMarkersHooks(markers);
        return marker;
      }
    }

    // 지도 위에 표시되고 있는 마커를 모두 제거합니다
    function removeMarker() {
      for (var i = 0; i < markers.length; i++) {
        markers[i].setMap(null);
      }
      markers = [];
      setMarkersHooks(markers);
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
      let className = this.className;
      placeOverlay.setMap(null);

      if (className === "on") {
        currCategory = "";
      } else {
        currCategory = id;
        searchPlaces();
      }
    }
    return () => {
      searchRef.current &&
        searchRef.current.removeEventListener("submit", addSubmitKeyword);
    };
  }, []);
  return (
    <div className={styles.HotplaceMap}>
      <div className={styles.mapContainer}>
        <div id="Map" className={styles.map} />
      </div>
    </div>
  );
};

export default HotplaceMap;
