import React, { memo, useCallback, useState } from "react";
import { DragDropContext, Draggable, Droppable } from "react-beautiful-dnd";
import styles from "./shoppingCart.module.css";
import ShoppingItem from "./shoppingItem/shoppingItem";
import { makeRoute } from "../../../api/routeAPI";
import InputRootName from "../inputRootName/inputRootName";
import { HistoryOutlined } from "@ant-design/icons";
import ResetConfirm from "../resetConfirm/resetConfirm";
import DeleteConfirm from "../deleteConfirm/deleteConfirm";
import { onEditOrder } from "../../../api/cartAPI";

const ShoppingCart = memo(
  ({
    items,
    updateShoppingCart,
    updateMemoShoppingItem,
    deleteClickedItemId,
    resetCartAll,
    onClose,
    deleteItemId,
    onDeleteItem,
  }) => {
    const [openInputName, setOpenInputName] = useState(false);
    const [openResetConfirm, setOpenResetConfirm] = useState(false);
    const [deleteConfirm, setDeleteConfirm] = useState(false);

    const onOpenInputName = useCallback(() => {
      if (items.length > 0) {
        setOpenInputName(true);
      }
    }, [items]);
    const onCloseInputName = useCallback(() => {
      setOpenInputName(false);
    }, []);
    const onOpenResetConfirm = useCallback(() => {
      if (items.length > 0) {
        setOpenResetConfirm(true);
      }
    }, [items]);
    const onCloseResetConfirm = useCallback(() => {
      setOpenResetConfirm(false);
    }, []);
    const onOpenDeleteConfirm = useCallback(() => {
      setDeleteConfirm(true);
    }, []);
    const onCloseDeleteConfirm = useCallback(() => {
      setDeleteConfirm(false);
    }, []);
    const onDragEnd = useCallback(
      (result) => {
        const { destination, source, reason } = result;
        if (!destination || reason === "CANCEL") {
          return;
        }
        if (destination.droppableId === source.droppableId && destination.index === source.index) {
          return;
        }
        const updateItems = [...items];
        const droppedItem = items[source.index];
        onEditOrder(droppedItem.id, updateItems[destination.index].id);
        // onEditOrder(updateItems[destination.index].id, droppedItem.id);

        updateItems.splice(source.index, 1);
        updateItems.splice(destination.index, 0, droppedItem);
        updateShoppingCart(updateItems);
      },
      [items, updateShoppingCart]
    );
    const onSaveRoute = useCallback(
      async (name) => {
        if (await makeRoute(name, items)) {
          resetCartAll();
          onClose();
        }
      },
      [items, onClose, resetCartAll]
    );
    return (
      <>
        <DragDropContext onDragEnd={onDragEnd}>
          <div className={styles.ShoppingCart}>
            <header className={styles.shoppingCartHeader}>
              <span className={styles.title}>나의 담은 목록</span>
              <div className={styles.header__btnBox}>
                <button className={styles.resetBtn} onClick={onOpenResetConfirm}>
                  <HistoryOutlined />
                </button>
                <button className={styles.closeBtn} onClick={onClose}>
                  X
                </button>
              </div>
            </header>
            <Droppable droppableId="list">
              {(provided) => (
                <ul className={styles.list} ref={provided.innerRef} {...provided.droppableProps}>
                  {items &&
                    items.map((item, index) => (
                      <Draggable key={item.id} draggableId={`${item.id}`} index={index}>
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
                              onOpenDeleteConfirm={onOpenDeleteConfirm}
                              deleteClickedItemId={deleteClickedItemId}
                              updateShoppingCart={updateShoppingCart}
                              updateMemoShoppingItem={updateMemoShoppingItem}
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
              <button className={styles.addRouteBtn} onClick={onOpenInputName}>
                경로 저장하기
              </button>
              <span>바른 여행 길잡이</span>
            </div>
          </div>
        </DragDropContext>
        {openInputName && <InputRootName onClose={onCloseInputName} onSaveRoute={onSaveRoute} />}
        {openResetConfirm && <ResetConfirm onReset={resetCartAll} onClose={onCloseResetConfirm} />}
        {deleteConfirm && (
          <DeleteConfirm deleteItemId={deleteItemId} onDeleteItem={onDeleteItem} onClose={onCloseDeleteConfirm} />
        )}
      </>
    );
  }
);

export default ShoppingCart;
