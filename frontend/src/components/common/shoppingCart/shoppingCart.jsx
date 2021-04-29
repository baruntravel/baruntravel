import React, { useCallback, useEffect, useRef, useState } from "react";
import { DragDropContext, Draggable, Droppable } from "react-beautiful-dnd";
import { useRecoilState } from "recoil";
import styles from "./shoppingCart.module.css";
import ShoppingItem from "./shoppingItem/shoppingItem";
import { postRoute } from "../../../api/routeAPI";
import InputRootName from "../inputRootName/inputRootName";

const ShoppingCart = ({
  items,
  setConfirmPortalTrue,
  updateShoppingCart,
  deleteClickedItemId,
  resetCartAll,
  // routeName,
}) => {
  // const routeName = "test경로"; // 경로 이름이 있을 경우 새로운 경로 이름 생성, (edit할 수도 있으니까?)
  const [openInputName, setOpenInputName] = useState(false);

  const setCloseInputName = useCallback(() => {
    setOpenInputName(false);
  }, []);
  const onDragEnd = useCallback((result) => {
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
  }, []);
  const onSaveRoute = useCallback(() => {
    console.log(items);
    setOpenInputName(true);
    // postRoute(items);
    resetCartAll();
  }, []);
  return (
    <>
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
                            updateShoppingCart={updateShoppingCart}
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
            <button className={styles.addRouteBtn} onClick={onSaveRoute}>
              경로 저장하기
            </button>
            <span>바른 여행 길잡이</span>
          </div>
        </div>
      </DragDropContext>
      {openInputName && <InputRootName onClose={setCloseInputName} />}
    </>
  );
};

export default ShoppingCart;
