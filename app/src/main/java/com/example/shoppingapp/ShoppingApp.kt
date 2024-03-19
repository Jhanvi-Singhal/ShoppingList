package com.example.shoppingapp

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

data class listItems(val id:Int , var itemname:String , var itemquantity:Int, var isEdit:Boolean= false){

}
@Composable
fun ShoppingList(){
    var sitem by remember{ mutableStateOf(listOf<listItems>()) }
    var name by remember{mutableStateOf("")}
    var quantity by remember{mutableStateOf("")}
    var alertdisplay by remember{mutableStateOf(false)}
    Column( modifier= Modifier.fillMaxSize())
    {

        Button(onClick = { /*TODO*/ alertdisplay=true}, modifier= Modifier.align(Alignment.CenterHorizontally)) {
            (Text(text = "Add Item"))
        }
        LazyColumn( modifier= Modifier
            .fillMaxSize()
            .padding(16.dp)){
            items(sitem){
                item->
                if (item.isEdit){
                    displayEdit(item = item , onEditComplete = {
                        editedName, editedQuantity->
                        sitem=sitem.map{it.copy(isEdit=false)}
                        val editedItem= sitem.find{it.id==item.id}
                         editedItem?.let{
                             it.itemname= editedName
                             it.itemquantity= editedQuantity
                         }
                    })
                
            }
                else{
                    displayItem(item = item, onEditClick = {

                        sitem=sitem.map{it.copy(isEdit= it.id==item.id)} }, onDeleteClick = {
                        sitem=sitem-item
                    })
                }
            }
        }
        if(alertdisplay){
        AlertDialog(onDismissRequest = { /*TODO*/ alertdisplay=false},
            confirmButton = {
                Row(modifier=Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                    Button(onClick = {
                        if(name.isNotBlank()){
                        val item=listItems(
                            id= sitem.size+1,
                            itemname= name,
                            itemquantity= quantity.toInt())
                            sitem= sitem + item
                            alertdisplay =false
                            name=""
                            quantity=""

                    }}) {
                        Text(text = "Add")
                    }
                    Button(onClick = { /*TODO*/ alertdisplay=false}) {
                        Text(text = "Cancel")
                    }
                }

        },
            title = {Text(text = "Add Item")},
            text= {
                Column {
                    OutlinedTextField(value = name,
                        onValueChange ={ name= it},
                        singleLine= true,
                        modifier= Modifier
                            .fillMaxWidth()
                            .padding(8.dp))
                    OutlinedTextField(value = quantity,
                        onValueChange ={ quantity=it},
                        singleLine= true,
                        modifier= Modifier
                            .fillMaxWidth()
                            .padding(8.dp) )
                }
            })

}}

}
@Composable
fun displayItem(
    item: listItems,
    onEditClick: ()->Unit,
    onDeleteClick: ()->Unit,

    )
{
    Row(modifier= Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .border(
            border = BorderStroke(2.dp, Color.Cyan,),
            shape = RoundedCornerShape(20)
        )){
        Text(text = item.itemname, modifier=Modifier.padding(8.dp))
        Text(text ="Qty: ${item.itemquantity}", modifier=Modifier.padding(8.dp))
        Spacer(modifier = Modifier.wrapContentSize(Alignment.TopEnd))
        IconButton(onClick = {  onEditClick()}) {
            Icon(imageVector= Icons.Default.Edit , contentDescription = null)
        }
        IconButton(onClick = {  onDeleteClick() }) {
            Icon(imageVector= Icons.Default.Delete , contentDescription = null)
        }
    }
}

@Composable
fun displayEdit(item:listItems,onEditComplete:(String,Int)->Unit){
    var editedname by remember{mutableStateOf(item.itemname)}
    var editedquantity by remember{mutableStateOf((item.itemquantity).toString())}
    var isEditing by remember{ mutableStateOf(item.isEdit) }
    Row(modifier= Modifier
        .fillMaxWidth()
        .padding(8.dp)){
        Column (modifier=Modifier.padding(8.dp)){
            BasicTextField(value = editedname, onValueChange = {editedname=it }, singleLine = true,modifier= Modifier
                .wrapContentSize()
                .padding(8.dp))
            BasicTextField(value = editedquantity, onValueChange = {editedquantity=it }, singleLine = true,modifier= Modifier
                .wrapContentSize()
                .padding(8.dp))
        }
        Button(onClick = {
            isEditing=false
            onEditComplete(editedname,editedquantity.toIntOrNull()?:1)
        }) {
            Text(text = "Save")
        }
    }

}




