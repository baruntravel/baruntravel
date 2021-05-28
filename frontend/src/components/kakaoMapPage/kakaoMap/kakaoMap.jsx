import React, { useCallback, useEffect, useRef, useState } from "react";
import styles from "./kakaoMap.module.css";

const { kakao } = window;
const KakaoMap = ({
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

  useEffect(() => {
    if (mapHooks) {
      mapHooks.panTo(new kakao.maps.LatLng(place.y, place.x));
      // markersHooks[markerIndex].T.Xj =
      markerIndex && markersHooks[markerIndex].setOpacity(0.5);
    }
  }, [place]);

  useEffect(() => {
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

    // 검색이 완료됐을 때를 위한 이벤트 등록
    searchRef.current.addEventListener("submit", addSubmitKeyword);

    // 카테고리 검색을 요청
    addCategoryClickEvent();
    // 키워드 검색을 요청
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
    function markerClickEvent(marker, place, index) {
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
          markerClickEvent(marker, places[i], i);
        }
      } else if (keyword) {
        removeMarker();
        for (let i = 0; i < places.length; i++) {
          let placePosition = new kakao.maps.LatLng(places[i].y, places[i].x);
          let marker = addMarker(placePosition, i);
          markerClickEvent(marker, places[i], i);
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
      currCategory = id;
      searchPlaces();
    }
    return () => {
      searchRef.current &&
        searchRef.current.removeEventListener("submit", addSubmitKeyword);
    };
  }, []);

  return (
    <div className={styles.KakaoMap}>
      <div className={styles.mapContainer}>
        <div id="Map" className={styles.map} />
      </div>
    </div>
  );
};

export default KakaoMap;
