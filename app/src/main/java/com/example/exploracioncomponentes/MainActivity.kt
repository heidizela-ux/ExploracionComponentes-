@file:OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class,
    ExperimentalMaterialApi::class
)

package com.example.exploracioncomponentes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BackdropScaffold
import androidx.compose.material.BackdropValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.exploracioncomponentes.ui.theme.ExploracionComponentesTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExploracionComponentesTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Catalogo()
                }
            }
        }
    }
}

/* ===================== CATÁLOGO (menú para abrir cada demo) ===================== */

private class Demo(val nombre: String, val contenido: @Composable () -> Unit)

private val demos = listOf(
    Demo("LazyColumn") { LazyColumnDemo() },
    Demo("LazyRow") { LazyRowDemo() },
    Demo("Grid") { GridDemo() },
    Demo("ConstraintLayout") { ConstraintLayoutDemo() },
    Demo("Scaffold") { ScaffoldDemo() },
    Demo("Surface") { SurfaceDemo() },
    Demo("Chip") { ChipDemo() },
    Demo("BackdropScaffold") { BackdropScaffoldDemo() },
    Demo("FlowRow") { FlowRowDemo() },
    Demo("FlowColumn") { FlowColumnDemo() },
    Demo("AlertDialog") { AlertDialogDemo() },
    Demo("Card") { CardDemo() },
    Demo("Checkbox") { CheckboxDemo() },
    Demo("FloatingActionButton") { FabDemo() },
    Demo("Icon") { IconDemo() },
    Demo("Image") { ImageDemo() },
    Demo("ProgressBar") { ProgressBarDemo() },
    Demo("RadioButton") { RadioButtonDemo() },
    Demo("Slider") { SliderDemo() },
    Demo("Spacer") { SpacerDemo() },
    Demo("Switch") { SwitchDemo() },
    Demo("TopAppBar") { TopAppBarDemo() },
    Demo("BottomNavigation") { BottomNavigationDemo() },
    Demo("Dialog") { DialogDemo() },
    Demo("Divider") { DividerDemo() },
    Demo("DropDownMenu") { DropDownMenuDemo() },
    Demo("LazyVerticalGrid") { LazyVerticalGridDemo() },
    Demo("NavigationRail") { NavigationRailDemo() },
    Demo("OutlinedTextField") { OutlinedTextFieldDemo() },
    Demo("Pager") { PagerDemo() },
    Demo("Snackbar") { SnackbarDemo() },
    Demo("TabRow") { TabRowDemo() },
    Demo("Tooltip") { TooltipDemo() },
)

@Composable
fun Catalogo() {
    var seleccionado by remember { mutableIntStateOf(-1) }
    if (seleccionado == -1) {
        LazyColumn(modifier = Modifier.systemBarsPadding()) {
            itemsIndexed(demos) { indice, demo ->
                ListItem(
                    headlineContent = { Text(demo.nombre) },
                    modifier = Modifier.clickable { seleccionado = indice }
                )
                HorizontalDivider()
            }
        }
    } else {
        BackHandler { seleccionado = -1 }   // botón "atrás" vuelve al menú
        Box(modifier = Modifier.fillMaxSize().systemBarsPadding()) {
            demos[seleccionado].contenido()
        }
    }
}

/* ============================== CONTENEDORES ============================== */

// 1. LazyColumn: lista vertical que solo compone lo visible (scroll)
@Preview(showBackground = true)
@Composable
fun LazyColumnDemo() {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(30) { i ->
            Text("Elemento número $i", modifier = Modifier.padding(16.dp))
            HorizontalDivider()
        }
    }
}

// 2. LazyRow: lista horizontal con scroll
@Preview(showBackground = true)
@Composable
fun LazyRowDemo() {
    LazyRow(
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(20) { i ->
            Card { Text("Tarjeta $i", modifier = Modifier.padding(24.dp)) }
        }
    }
}

// 3. Grid: cuadrícula armada con Column + Row y weight (Grid nativo es experimental)
@Preview(showBackground = true)
@Composable
fun GridDemo() {
    Column(modifier = Modifier.padding(16.dp)) {
        repeat(3) { fila ->
            Row {
                repeat(3) { col ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .padding(4.dp)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) { Text("${fila * 3 + col + 1}") }
                }
            }
        }
    }
}

// 4. ConstraintLayout: posiciona elementos con restricciones entre ellos
@Preview(showBackground = true)
@Composable
fun ConstraintLayoutDemo() {
    ConstraintLayout(modifier = Modifier.fillMaxWidth().height(220.dp)) {
        val (titulo, subtitulo, boton) = createRefs()
        Text(
            "Título",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.constrainAs(titulo) {
                top.linkTo(parent.top, 16.dp)
                start.linkTo(parent.start, 16.dp)
            }
        )
        Text(
            "Subtítulo debajo del título",
            modifier = Modifier.constrainAs(subtitulo) {
                top.linkTo(titulo.bottom, 8.dp)
                start.linkTo(titulo.start)
            }
        )
        Button(
            onClick = {},
            modifier = Modifier.constrainAs(boton) {
                bottom.linkTo(parent.bottom, 16.dp)
                end.linkTo(parent.end, 16.dp)
            }
        ) { Text("Aceptar") }
    }
}

// 5. Scaffold: estructura base de pantalla (topBar, bottomBar, FAB, contenido)
@Preview(showBackground = true)
@Composable
fun ScaffoldDemo() {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Scaffold") }) },
        bottomBar = { BottomAppBar { Text("Barra inferior", modifier = Modifier.padding(16.dp)) } },
        floatingActionButton = {
            FloatingActionButton(onClick = {}) { Icon(Icons.Default.Add, "Agregar") }
        }
    ) { padding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentAlignment = Alignment.Center
        ) { Text("Contenido principal") }
    }
}

// 6. Surface: superficie con forma, color y elevación
@Preview(showBackground = true)
@Composable
fun SurfaceDemo() {
    Surface(
        modifier = Modifier.padding(24.dp),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.primaryContainer,
        tonalElevation = 4.dp,
        shadowElevation = 6.dp
    ) {
        Text("Texto sobre una Surface", modifier = Modifier.padding(32.dp))
    }
}

// 7. Chip: etiquetas compactas (Assist / Filter)
@Preview(showBackground = true)
@Composable
fun ChipDemo() {
    var seleccionado by remember { mutableStateOf(false) }
    Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        AssistChip(onClick = {}, label = { Text("AssistChip") })
        FilterChip(
            selected = seleccionado,
            onClick = { seleccionado = !seleccionado },
            label = { Text("FilterChip") },
            leadingIcon = { if (seleccionado) Icon(Icons.Default.Check, contentDescription = null) }
        )
    }
}

// 8. BackdropScaffold (Material 2): capa trasera + capa frontal
@Preview(showBackground = true)
@Composable
fun BackdropScaffoldDemo() {
    val estado = rememberBackdropScaffoldState(BackdropValue.Concealed)
    BackdropScaffold(
        scaffoldState = estado,
        appBar = { androidx.compose.material.TopAppBar(title = { Text("Backdrop") }) },
        backLayerContent = {
            Box(modifier = Modifier.fillMaxWidth().height(160.dp), contentAlignment = Alignment.Center) {
                Text("Capa trasera (menú / filtros)")
            }
        },
        frontLayerContent = {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Capa frontal (contenido)")
            }
        },
        peekHeight = 120.dp
    )
}

// 9. FlowRow: fila que salta de línea automáticamente
@Preview(showBackground = true)
@Composable
fun FlowRowDemo() {
    FlowRow(
        modifier = Modifier.padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        listOf("Kotlin", "Compose", "Android", "Material", "Layouts", "Row", "Column", "Preview", "Git")
            .forEach { AssistChip(onClick = {}, label = { Text(it) }) }
    }
}

// 10. FlowColumn: columna que salta a otra columna al llenarse
@Preview(showBackground = true)
@Composable
fun FlowColumnDemo() {
    FlowColumn(
        modifier = Modifier.height(170.dp).padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(10) { AssistChip(onClick = {}, label = { Text("Item $it") }) }
    }
}

/* ================================ CONTROLES ================================ */

// 11. AlertDialog: diálogo de confirmación estándar
@Preview(showBackground = true)
@Composable
fun AlertDialogDemo() {
    var abierto by remember { mutableStateOf(true) }
    if (abierto) {
        AlertDialog(
            onDismissRequest = { abierto = false },
            title = { Text("¿Eliminar elemento?") },
            text = { Text("Esta acción no se puede deshacer.") },
            confirmButton = { TextButton(onClick = { abierto = false }) { Text("Eliminar") } },
            dismissButton = { TextButton(onClick = { abierto = false }) { Text("Cancelar") } }
        )
    } else {
        Button(onClick = { abierto = true }, modifier = Modifier.padding(16.dp)) { Text("Mostrar diálogo") }
    }
}

// 12. Card: tarjeta con elevación
@Preview(showBackground = true)
@Composable
fun CardDemo() {
    Card(
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Título de la tarjeta", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))
            Text("Contenido descriptivo dentro de una Card.")
        }
    }
}

// 13. Checkbox: casilla de verificación
@Preview(showBackground = true)
@Composable
fun CheckboxDemo() {
    var marcado by remember { mutableStateOf(true) }
    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = marcado, onCheckedChange = { marcado = it })
        Text("Acepto los términos y condiciones")
    }
}

// 14. FloatingActionButton: botón flotante de acción principal
@Preview(showBackground = true)
@Composable
fun FabDemo() {
    Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        FloatingActionButton(onClick = {}) { Icon(Icons.Default.Add, "Agregar") }
        ExtendedFloatingActionButton(
            onClick = {},
            icon = { Icon(Icons.Default.Edit, null) },
            text = { Text("Editar") }
        )
    }
}

// 15. Icon: íconos vectoriales con color (tint)
@Preview(showBackground = true)
@Composable
fun IconDemo() {
    Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Icon(Icons.Default.Home, "Inicio", modifier = Modifier.size(40.dp))
        Icon(Icons.Default.Favorite, "Favorito", tint = Color.Red, modifier = Modifier.size(40.dp))
        Icon(Icons.Default.Settings, "Ajustes", tint = Color(0xFF1B5E20), modifier = Modifier.size(40.dp))
    }
}

// 16. Image: muestra una imagen (drawable)
@Preview(showBackground = true)
@Composable
fun ImageDemo() {
    Image(
        painter = painterResource(id = R.drawable.ic_launcher_foreground),
        contentDescription = "Imagen de ejemplo",
        modifier = Modifier.padding(16.dp).size(120.dp)
    )
}

// 17. ProgressBar: indicadores de progreso (lineal y circular)
@Preview(showBackground = true)
@Composable
fun ProgressBarDemo() {
    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        LinearProgressIndicator(progress = { 0.6f }, modifier = Modifier.fillMaxWidth())
        CircularProgressIndicator()
    }
}

// 18. RadioButton: selección única dentro de un grupo
@Preview(showBackground = true)
@Composable
fun RadioButtonDemo() {
    val opciones = listOf("Kotlin", "Java", "Dart")
    var elegida by remember { mutableStateOf(opciones[0]) }
    Column(modifier = Modifier.padding(16.dp)) {
        opciones.forEach { opcion ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(selected = elegida == opcion, onClick = { elegida = opcion })
                Text(opcion)
            }
        }
    }
}

// 19. Slider: selecciona un valor en un rango
@Preview(showBackground = true)
@Composable
fun SliderDemo() {
    var valor by remember { mutableFloatStateOf(0.3f) }
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Valor: ${(valor * 100).toInt()}")
        Slider(value = valor, onValueChange = { valor = it }, valueRange = 0f..1f)
    }
}

// 20. Spacer: espacio vacío entre elementos
@Preview(showBackground = true)
@Composable
fun SpacerDemo() {
    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.size(60.dp).background(Color(0xFF1565C0)))
        Spacer(modifier = Modifier.width(48.dp))
        Box(Modifier.size(60.dp).background(Color(0xFFC62828)))
    }
}

// 21. Switch: interruptor on/off
@Preview(showBackground = true)
@Composable
fun SwitchDemo() {
    var activo by remember { mutableStateOf(true) }
    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
        Switch(checked = activo, onCheckedChange = { activo = it })
        Spacer(Modifier.width(12.dp))
        Text(if (activo) "Modo oscuro: ON" else "Modo oscuro: OFF")
    }
}

// 22. TopAppBar: barra superior con título, navegación y acciones
@Preview(showBackground = true)
@Composable
fun TopAppBarDemo() {
    TopAppBar(
        title = { Text("Mi aplicación") },
        navigationIcon = { IconButton(onClick = {}) { Icon(Icons.Default.Menu, "Menú") } },
        actions = { IconButton(onClick = {}) { Icon(Icons.Default.Search, "Buscar") } }
    )
}

// 23. BottomNavigation (en Material 3 se llama NavigationBar)
@Preview(showBackground = true)
@Composable
fun BottomNavigationDemo() {
    var indice by remember { mutableIntStateOf(0) }
    val items = listOf("Inicio" to Icons.Default.Home, "Buscar" to Icons.Default.Search, "Perfil" to Icons.Default.Person)
    NavigationBar {
        items.forEachIndexed { i, (texto, icono) ->
            NavigationBarItem(
                selected = indice == i,
                onClick = { indice = i },
                icon = { Icon(icono, texto) },
                label = { Text(texto) }
            )
        }
    }
}

// 24. Dialog: ventana emergente con contenido personalizado
@Preview(showBackground = true)
@Composable
fun DialogDemo() {
    Dialog(onDismissRequest = {}) {
        Card {
            Column(modifier = Modifier.padding(24.dp)) {
                Text("Dialog personalizado", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(8.dp))
                Text("Aquí puedes poner cualquier contenido.")
            }
        }
    }
}

// 25. Divider (Material 3: HorizontalDivider / VerticalDivider)
@Preview(showBackground = true)
@Composable
fun DividerDemo() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Arriba")
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), thickness = 2.dp)
        Text("Abajo")
        Spacer(Modifier.height(16.dp))
        Row(modifier = Modifier.height(IntrinsicSize.Min), verticalAlignment = Alignment.CenterVertically) {
            Text("Izquierda")
            VerticalDivider(modifier = Modifier.padding(horizontal = 12.dp), thickness = 2.dp)
            Text("Derecha")
        }
    }
}

// 26. DropDownMenu: menú desplegable
@Preview(showBackground = true)
@Composable
fun DropDownMenuDemo() {
    var expandido by remember { mutableStateOf(true) }
    Box(modifier = Modifier.padding(16.dp)) {
        Button(onClick = { expandido = true }) { Text("Abrir menú") }
        DropdownMenu(expanded = expandido, onDismissRequest = { expandido = false }) {
            DropdownMenuItem(text = { Text("Opción 1") }, onClick = { expandido = false })
            DropdownMenuItem(text = { Text("Opción 2") }, onClick = { expandido = false })
            DropdownMenuItem(text = { Text("Opción 3") }, onClick = { expandido = false })
        }
    }
}

// 27. LazyVerticalGrid: cuadrícula con scroll
@Preview(showBackground = true)
@Composable
fun LazyVerticalGridDemo() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(24) { i ->
            Box(
                modifier = Modifier.aspectRatio(1f).background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) { Text("$i", color = MaterialTheme.colorScheme.onPrimary) }
        }
    }
}

// 28. NavigationRail: barra de navegación lateral (tablets / pantallas anchas)
@Preview(showBackground = true)
@Composable
fun NavigationRailDemo() {
    var indice by remember { mutableIntStateOf(0) }
    val items = listOf("Inicio" to Icons.Default.Home, "Buscar" to Icons.Default.Search, "Perfil" to Icons.Default.Person)
    Row(modifier = Modifier.fillMaxSize()) {
        NavigationRail {
            items.forEachIndexed { i, (texto, icono) ->
                NavigationRailItem(
                    selected = indice == i,
                    onClick = { indice = i },
                    icon = { Icon(icono, texto) },
                    label = { Text(texto) }
                )
            }
        }
        Box(modifier = Modifier.weight(1f).fillMaxHeight(), contentAlignment = Alignment.Center) {
            Text("Sección: ${items[indice].first}")
        }
    }
}

// 29. OutlinedTextField: campo de texto con borde
@Preview(showBackground = true)
@Composable
fun OutlinedTextFieldDemo() {
    var texto by remember { mutableStateOf("") }
    OutlinedTextField(
        value = texto,
        onValueChange = { texto = it },
        label = { Text("Nombre") },
        modifier = Modifier.padding(16.dp)
    )
}

// 30. Pager: páginas deslizables horizontalmente
@Preview(showBackground = true)
@Composable
fun PagerDemo() {
    val estado = rememberPagerState(pageCount = { 3 })
    HorizontalPager(state = estado, modifier = Modifier.fillMaxWidth().height(220.dp)) { pagina ->
        Box(
            modifier = Modifier.fillMaxSize().background(
                listOf(Color(0xFFBBDEFB), Color(0xFFC8E6C9), Color(0xFFFFE0B2))[pagina]
            ),
            contentAlignment = Alignment.Center
        ) { Text("Página ${pagina + 1} de 3 (desliza)") }
    }
}

// 31. Snackbar: mensaje breve temporal (estático + interactivo)
@Preview(showBackground = true)
@Composable
fun SnackbarDemo() {
    val host = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(snackbarHost = { SnackbarHost(host) }) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            Snackbar(action = { TextButton(onClick = {}) { Text("Deshacer") } }) {
                Text("Snackbar estático (vista previa)")
            }
            Spacer(Modifier.height(16.dp))
            Button(onClick = { scope.launch { host.showSnackbar("Mensaje enviado") } }) {
                Text("Mostrar Snackbar")
            }
        }
    }
}

// 32. TabRow: pestañas
@Preview(showBackground = true)
@Composable
fun TabRowDemo() {
    var pestana by remember { mutableIntStateOf(0) }
    val titulos = listOf("Inicio", "Buscar", "Perfil")
    Column {
        TabRow(selectedTabIndex = pestana) {
            titulos.forEachIndexed { i, titulo ->
                Tab(selected = pestana == i, onClick = { pestana = i }, text = { Text(titulo) })
            }
        }
        Text("Contenido de: ${titulos[pestana]}", modifier = Modifier.padding(24.dp))
    }
}

// 33. Tooltip: texto de ayuda (mantén presionado el ícono en el emulador)
@Preview(showBackground = true)
@Composable
fun TooltipDemo() {
    TooltipBox(
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
        tooltip = { PlainTooltip { Text("Agregar a favoritos") } },
        state = rememberTooltipState()
    ) {
        IconButton(onClick = {}, modifier = Modifier.padding(24.dp)) {
            Icon(Icons.Default.Favorite, contentDescription = "Favorito")
        }
    }
}
