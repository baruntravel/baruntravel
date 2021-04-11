import React, { useCallback, useRef, useState } from "react";
import { DragDropContext, Draggable, Droppable } from "react-beautiful-dnd";
import { useRecoilState } from "recoil";
import { testPlaces } from "../../../recoil/routeAtom";
import styles from "./shoppingCart.module.css";
import ShoppingItem from "./shoppingItem/shoppingItem";

const ShoppingCart = ({ items, setConfirmPortalTrue }) => {
  const [testItems, setTestItems] = useState([
    {
      id: "688578118",
      place_name: "BNK저축은행 리테일금융센터",
      place_url: "http://place.map.kakao.com/688578118",
      address_name: "서울 중구 무교로 6",
      x: "126.97943787116054",
      y: "37.56657026127022",
    },
    {
      id: "508437738",
      place_name: "신한은행 서울시청금융센터",
      placeUrl: "http://place.map.kakao.com/508437738",
      address_name: "서울 중구 세종대로 110",
      x: "126.978244947578",
      y: "37.5662231640346",
    },
    {
      id: "7975883",
      place_name: "신한은행 서울광장출장소",
      placeUrl: "http://place.map.kakao.com/7975883",
      address_name: "서울 중구 을지로 16",
      x: "126.979476558519",
      y: "37.5658314512941",
    },
  ]); // recoil을 사용하면 렌더링이 두번 거쳐져서 items로 받아와야한다.
  const onDragEnd = (result) => {
    const { destination, source, reason } = result;
    if (!destination || reason === "CANCEL") {
      return;
    }
    if (
      destination.droppableId === source.droppableId &&
      destination.index === source.index
    ) {
      return;
    }
    const updateItems = [...testItems];
    const droppedItem = testItems[source.index];
    updateItems.splice(source.index, 1);
    updateItems.splice(destination.index, 0, droppedItem);
    setTestItems(updateItems);
  };
  return (
    <DragDropContext onDragEnd={onDragEnd}>
      <div className={styles.ShoppingCart}>
        <Droppable droppableId="list">
          {(provided) => (
            <ul
              className={styles.list}
              ref={provided.innerRef}
              {...provided.droppableProps}
            >
              {testItems.map((item, index) => (
                <Draggable
                  key={item.id}
                  draggableId={`${item.id}`}
                  index={index}
                >
                  {(provided) => (
                    <div
                      key={index}
                      className={styles.itemContainer}
                      ref={provided.innerRef}
                      {...provided.draggableProps}
                      {...provided.dragHandleProps}
                    >
                      <ShoppingItem
                        key={index}
                        item={item}
                        setConfirmPortalTrue={setConfirmPortalTrue}
                      />
                    </div>
                  )}
                </Draggable>
              ))}
              {provided.placeholder}
            </ul>
          )}
        </Droppable>
        <div className={styles.bottom}>
          <button className={styles.addRouteBtn}>경로 저장하기</button>
          <span>바른 여행 길잡이</span>
        </div>
      </div>
    </DragDropContext>
  );
};

export default ShoppingCart;
