import React, { useCallback, useEffect, useRef, useState } from "react";
import { DragDropContext, Draggable, Droppable } from "react-beautiful-dnd";
import { useRecoilState } from "recoil";
import { testPlaces } from "../../../recoil/routeAtom";
import styles from "./shoppingCart.module.css";
import ShoppingItem from "./shoppingItem/shoppingItem";

const ShoppingCart = ({
  items,
  setConfirmPortalTrue,
  updateShoppingCart,
  deleteClickedItemId,
}) => {
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
    const updateItems = [...items];
    const droppedItem = items[source.index];
    updateItems.splice(source.index, 1);
    updateItems.splice(destination.index, 0, droppedItem);
    updateShoppingCart(updateItems);
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
              {items &&
                items.map((item, index) => (
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
                          deleteClickedItemId={deleteClickedItemId}
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
