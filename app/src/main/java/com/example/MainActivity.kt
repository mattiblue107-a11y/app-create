package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MuseDiscoveryApp()
            }
        }
    }
}

data class VisualIdea(
    val id: String,
    val title: String,
    val category: String,
    val subtitle: String,
    val description: String,
    @DrawableRes val imageRes: Int? = null,
    val iconType: String? = null,
    val isSpan2: Boolean = false,
    val isLightCard: Boolean = false,
    val colorPalette: List<String> = listOf("#D0BCFF", "#381E72", "#49454F", "#1C1B1F"),
    val tags: List<String> = listOf("Diseño", "Inspiración"),
    val likesCount: Int = 120,
    val isTrending: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MuseDiscoveryApp() {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Todo") }
    var onlySaved by remember { mutableStateOf(false) }
    
    var savedIds by remember { mutableStateOf(setOf("1", "4")) }
    var selectedIdea by remember { mutableStateOf<VisualIdea?>(null) }
    var showAddDialog by remember { mutableStateOf(false) }

    var ideasList by remember {
        mutableStateOf(
            listOf(
                VisualIdea(
                    id = "1",
                    title = "Geometría Sagrada",
                    category = "Editorial",
                    subtitle = "Tendencia de Diseño Gráfico",
                    description = "Exploración visual minimalista combinando formas geométricas puras con acentos cromáticos cálidos sobre fondos carbón profundos.",
                    imageRes = R.drawable.img_hero_editorial_1782332188653,
                    isSpan2 = true,
                    colorPalette = listOf("#F4B41A", "#1C1B1F", "#332D41", "#E6E1E9"),
                    tags = listOf("Minimalismo", "Tipografía", "Geometría"),
                    likesCount = 384,
                    isTrending = true
                ),
                VisualIdea(
                    id = "2",
                    title = "Formas Fluidas",
                    category = "Arquitectura",
                    subtitle = "240 Inspiraciones",
                    description = "Curaduría de estructuras paramétricas y transiciones espaciales orgánicas inspiradas en la naturaleza.",
                    iconType = "domain",
                    isSpan2 = false,
                    colorPalette = listOf("#D0BCFF", "#49454F", "#2B2930", "#CAC4D0"),
                    tags = listOf("Arquitectura", "Orgánico", "3D"),
                    likesCount = 210
                ),
                VisualIdea(
                    id = "3",
                    title = "Paletas 2024",
                    category = "Paletas",
                    subtitle = "Colección Curada",
                    description = "Armonías cromáticas seleccionadas para interfaces digitales y mundos inmersivos.",
                    iconType = "palette",
                    isSpan2 = false,
                    isLightCard = true,
                    colorPalette = listOf("#21005D", "#EADDFF", "#D0BCFF", "#381E72"),
                    tags = listOf("Color", "UI", "Tendencia"),
                    likesCount = 520
                ),
                VisualIdea(
                    id = "4",
                    title = "Brutalismo Ciberpunk",
                    category = "Ciberpunk",
                    subtitle = "Volumetría Neón",
                    description = "Interiores arquitectónicos futuristas con iluminación volumétrica ámbar y cian contrastando con muros de concreto aparente.",
                    imageRes = R.drawable.img_hero_cyber_1782332178248,
                    isSpan2 = true,
                    colorPalette = listOf("#FFB800", "#00F0FF", "#1A1A24", "#4A4A5A"),
                    tags = listOf("Ciberpunk", "Iluminación", "Concreto"),
                    likesCount = 892,
                    isTrending = true
                ),
                VisualIdea(
                    id = "5",
                    title = "Jardín Bioluminiscente",
                    category = "Naturaleza",
                    subtitle = "Concept Art Botánico",
                    description = "Invernadero de cristal al crepúsculo con flora exótica resplandeciendo en rayos de luz púrpura y turquesa.",
                    imageRes = R.drawable.img_hero_nature_1782332201716,
                    isSpan2 = true,
                    colorPalette = listOf("#A855F7", "#14B8A6", "#0F172A", "#38BDF8"),
                    tags = listOf("Botánica", "Fantasía", "Luz"),
                    likesCount = 645
                ),
                VisualIdea(
                    id = "6",
                    title = "Bocetos Digitales",
                    category = "Minimalismo",
                    subtitle = "Sigue explorando lo nuevo",
                    description = "Espacio de experimentación rápida para capturar trazos conceptuales y mapas mentales visuales.",
                    iconType = "brush",
                    isSpan2 = true,
                    colorPalette = listOf("#D0BCFF", "#332D41", "#1C1B1F", "#E6E1E9"),
                    tags = listOf("Boceto", "Ideación", "Prototipo"),
                    likesCount = 156
                )
            )
        )
    }

    val categories = listOf("Todo", "Editorial", "Arquitectura", "Minimalismo", "Ciberpunk", "Naturaleza", "Paletas")

    val filteredIdeas = remember(ideasList, searchQuery, selectedCategory, onlySaved, savedIds) {
        ideasList.filter { idea ->
            val matchQuery = searchQuery.isBlank() ||
                idea.title.contains(searchQuery, ignoreCase = true) ||
                idea.tags.any { it.contains(searchQuery, ignoreCase = true) }
            val matchCategory = selectedCategory == "Todo" || idea.category.equals(selectedCategory, ignoreCase = true)
            val matchSaved = !onlySaved || savedIds.contains(idea.id)
            matchQuery && matchCategory && matchSaved
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize().background(ElegantDarkBg),
        containerColor = ElegantDarkBg,
        contentWindowInsets = WindowInsets.systemBars,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { showAddDialog = true },
                containerColor = ElegantPrimary,
                contentColor = ElegantOnPrimary,
                modifier = Modifier.testTag("add_idea_fab"),
                icon = { Icon(Icons.Default.Add, contentDescription = "Añadir idea") },
                text = { Text("Crear Idea", fontWeight = FontWeight.Bold) }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            // Header: Search & Profile
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(CircleShape)
                    .background(ElegantContainer)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar",
                    tint = ElegantTextMuted,
                    modifier = Modifier.size(22.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                BasicTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyMedium.copy(color = ElegantText),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("search_input"),
                    decorationBox = { innerTextField ->
                        Box(contentAlignment = Alignment.CenterStart) {
                            if (searchQuery.isEmpty()) {
                                Text(
                                    "Explorar ideas visuales...",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = ElegantTextMuted
                                )
                            }
                            innerTextField()
                        }
                    }
                )
                if (searchQuery.isNotEmpty()) {
                    IconButton(
                        onClick = { searchQuery = "" },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Limpiar búsqueda",
                            tint = ElegantTextMuted,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(ElegantPrimary)
                        .clickable {
                            Toast.makeText(context, "Coleccionista de Ideas: JD", Toast.LENGTH_SHORT).show()
                        }
                        .testTag("profile_avatar"),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "JD",
                        color = ElegantOnPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Categories & Saved filter
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = onlySaved,
                    onClick = { onlySaved = !onlySaved },
                    label = { Text("❤️ Guardados (${savedIds.size})") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Color(0xFFEFB8C8),
                        selectedLabelColor = Color(0xFF492532),
                        containerColor = ElegantSurface,
                        labelColor = ElegantText
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        enabled = true,
                        selected = onlySaved,
                        borderColor = ElegantOutline
                    ),
                    modifier = Modifier.testTag("filter_saved").heightIn(min = 36.dp)
                )

                categories.forEach { cat ->
                    val isSelected = !onlySaved && selectedCategory == cat
                    FilterChip(
                        selected = isSelected,
                        onClick = {
                            onlySaved = false
                            selectedCategory = cat
                        },
                        label = { Text(if (cat == "Todo") "Para ti" else cat) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = ElegantPrimary,
                            selectedLabelColor = ElegantOnPrimary,
                            containerColor = ElegantContainer,
                            labelColor = ElegantText
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = isSelected,
                            borderColor = ElegantOutline
                        ),
                        modifier = Modifier.testTag("filter_$cat").heightIn(min = 36.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Visual Discovery Masonry Grid
            if (filteredIdeas.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize().weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.AutoAwesome,
                            contentDescription = "Sin resultados",
                            tint = ElegantTextMuted,
                            modifier = Modifier.size(56.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            "No se encontraron inspiraciones visuales.",
                            color = ElegantTextMuted,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextButton(onClick = {
                            searchQuery = ""
                            selectedCategory = "Todo"
                            onlySaved = false
                        }) {
                            Text("Explorar todo", color = ElegantPrimary)
                        }
                    }
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    items(
                        items = filteredIdeas,
                        key = { it.id },
                        span = { item -> GridItemSpan(if (item.isSpan2) 2 else 1) }
                    ) { idea ->
                        InspirationCard(
                            idea = idea,
                            isSaved = savedIds.contains(idea.id),
                            onCardClick = { selectedIdea = idea },
                            onSaveClick = {
                                savedIds = if (savedIds.contains(idea.id)) {
                                    savedIds - idea.id
                                } else {
                                    savedIds + idea.id
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    // Detail Modal
    selectedIdea?.let { idea ->
        IdeaDetailModal(
            idea = idea,
            isSaved = savedIds.contains(idea.id),
            onDismiss = { selectedIdea = null },
            onSaveToggle = {
                savedIds = if (savedIds.contains(idea.id)) {
                    savedIds - idea.id
                } else {
                    savedIds + idea.id
                }
            }
        )
    }

    // Add Idea Modal
    if (showAddDialog) {
        AddIdeaModal(
            categories = categories.filter { it != "Todo" },
            onDismiss = { showAddDialog = false },
            onAddIdea = { title, cat, desc ->
                val newIdea = VisualIdea(
                    id = System.currentTimeMillis().toString(),
                    title = title,
                    category = cat,
                    subtitle = "Creación comunitaria",
                    description = desc,
                    iconType = "auto_awesome",
                    isSpan2 = true,
                    tags = listOf(cat, "Novedad", "Idea"),
                    likesCount = 1
                )
                ideasList = listOf(newIdea) + ideasList
                showAddDialog = false
                Toast.makeText(context, "Inspiración agregada a Muse", Toast.LENGTH_SHORT).show()
            }
        )
    }
}

@Composable
fun InspirationCard(
    idea: VisualIdea,
    isSaved: Boolean,
    onCardClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    val cardBg = when {
        idea.isLightCard -> ElegantSecondary
        else -> ElegantCardVariant
    }
    val textColor = if (idea.isLightCard) ElegantOnSecondary else ElegantText
    val mutedColor = if (idea.isLightCard) ElegantOnSecondary.copy(alpha = 0.7f) else ElegantTextMuted

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(if (idea.isSpan2 && idea.imageRes != null) 210.dp else 160.dp)
            .clip(RoundedCornerShape(24.dp))
            .border(1.dp, ElegantOutline, RoundedCornerShape(24.dp))
            .clickable(onClick = onCardClick)
            .testTag("card_${idea.id}"),
        colors = CardDefaults.cardColors(containerColor = cardBg)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (idea.imageRes != null) {
                Image(
                    painter = painterResource(idea.imageRes),
                    contentDescription = idea.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, ElegantDarkBg.copy(alpha = 0.9f)),
                                startY = 50f
                            )
                        )
                )
            } else if (idea.iconType != null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.linearGradient(
                                colors = if (idea.isLightCard) {
                                    listOf(Color(0xFFEADDFF), Color(0xFFD0BCFF))
                                } else {
                                    listOf(Color(0xFF49454F), Color(0xFF2B2930))
                                }
                            )
                        )
                        .padding(16.dp),
                    contentAlignment = Alignment.TopStart
                ) {
                    val iconVector = when (idea.iconType) {
                        "domain" -> Icons.Default.Domain
                        "palette" -> Icons.Default.ColorLens
                        "brush" -> Icons.Default.Brush
                        else -> Icons.Default.AutoAwesome
                    }
                    Icon(
                        imageVector = iconVector,
                        contentDescription = idea.category,
                        tint = if (idea.isLightCard) ElegantOnSecondary else ElegantPrimary,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            // Bookmark Button Top Right
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(10.dp)
            ) {
                IconButton(
                    onClick = onSaveClick,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(ElegantDarkBg.copy(alpha = 0.6f))
                        .testTag("bookmark_${idea.id}")
                ) {
                    Icon(
                        imageVector = if (isSaved) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = if (isSaved) "Quitar de guardados" else "Guardar en colección",
                        tint = if (isSaved) Color(0xFFEFB8C8) else Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Content Bottom
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                if (idea.isTrending) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .background(ElegantPrimary.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = ElegantPrimary, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("TENDENCIA", color = ElegantPrimary, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                } else {
                    Text(
                        text = idea.category.uppercase(),
                        color = if (idea.imageRes != null) ElegantPrimary else mutedColor,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }
                Text(
                    text = idea.title,
                    color = if (idea.imageRes != null) Color.White else textColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = idea.subtitle,
                    color = if (idea.imageRes != null) Color(0xFFCAC4D0) else mutedColor,
                    fontSize = 11.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun IdeaDetailModal(
    idea: VisualIdea,
    isSaved: Boolean,
    onDismiss: () -> Unit,
    onSaveToggle: () -> Unit
) {
    val context = LocalContext.current
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(28.dp))
                .border(1.dp, ElegantOutline, RoundedCornerShape(28.dp)),
            colors = CardDefaults.cardColors(containerColor = ElegantSurface)
        ) {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .background(ElegantCardVariant)
                ) {
                    if (idea.imageRes != null) {
                        Image(
                            painter = painterResource(idea.imageRes),
                            contentDescription = idea.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize().background(
                                Brush.linearGradient(listOf(Color(0xFF49454F), Color(0xFF2B2930)))
                            ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = ElegantPrimary, modifier = Modifier.size(64.dp))
                        }
                    }
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(12.dp)
                            .size(36.dp)
                            .background(ElegantDarkBg.copy(alpha = 0.6f), CircleShape)
                            .testTag("close_detail_button")
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Cerrar", tint = Color.White)
                    }
                }

                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = ElegantContainer,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = idea.category,
                                color = ElegantPrimary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Favorite, contentDescription = null, tint = Color(0xFFEFB8C8), modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("${idea.likesCount + if (isSaved) 1 else 0}", color = ElegantTextMuted, fontSize = 13.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = idea.title,
                        style = MaterialTheme.typography.headlineSmall,
                        color = ElegantText,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = idea.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = ElegantTextMuted,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Paleta de Color Cromática", color = ElegantText, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        idea.colorPalette.forEach { hex ->
                            val parsedColor = try {
                                Color(android.graphics.Color.parseColor(hex))
                            } catch (e: Exception) {
                                ElegantPrimary
                            }
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(38.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(parsedColor)
                                    .border(1.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(10.dp))
                                    .clickable {
                                        val clipboard = context.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                                        val clip = android.content.ClipData.newPlainText("Color Hex", hex)
                                        clipboard.setPrimaryClip(clip)
                                        Toast.makeText(context, "Color $hex copiado", Toast.LENGTH_SHORT).show()
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = hex,
                                    color = if (hex == "#1C1B1F" || hex == "#0F172A" || hex == "#1A1A24") Color.White else Color.Black,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        idea.tags.forEach { tag ->
                            Text(
                                text = "#$tag",
                                color = ElegantPrimary,
                                fontSize = 12.sp,
                                modifier = Modifier
                                    .background(ElegantCardVariant, RoundedCornerShape(6.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = onSaveToggle,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("save_collection_button"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSaved) Color(0xFFEFB8C8) else ElegantPrimary,
                            contentColor = if (isSaved) Color(0xFF492532) else ElegantOnPrimary
                        ),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Icon(
                            if (isSaved) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            if (isSaved) "Guardado en Museo" else "Guardar en Colección",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AddIdeaModal(
    categories: List<String>,
    onDismiss: () -> Unit,
    onAddIdea: (String, String, String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var selectedCat by remember { mutableStateOf(categories.firstOrNull() ?: "Editorial") }
    var desc by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(28.dp))
                .border(1.dp, ElegantOutline, RoundedCornerShape(28.dp)),
            colors = CardDefaults.cardColors(containerColor = ElegantSurface)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    "Crear Inspiración Visual",
                    style = MaterialTheme.typography.headlineSmall,
                    color = ElegantText,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Comparte un concepto o nota creativa con la comunidad de Muse.",
                    color = ElegantTextMuted,
                    fontSize = 13.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Título de la idea") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth().testTag("input_title"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = ElegantText,
                        unfocusedTextColor = ElegantText,
                        focusedContainerColor = ElegantDarkBg,
                        unfocusedContainerColor = ElegantDarkBg,
                        focusedBorderColor = ElegantPrimary,
                        unfocusedBorderColor = ElegantOutline
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))
                Text("Categoría", color = ElegantTextMuted, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    categories.forEach { cat ->
                        FilterChip(
                            selected = selectedCat == cat,
                            onClick = { selectedCat = cat },
                            label = { Text(cat) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = ElegantPrimary,
                                selectedLabelColor = ElegantOnPrimary,
                                containerColor = ElegantContainer,
                                labelColor = ElegantText
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = desc,
                    onValueChange = { desc = it },
                    label = { Text("Descripción o Prompt de diseño") },
                    minLines = 3,
                    maxLines = 5,
                    modifier = Modifier.fillMaxWidth().testTag("input_desc"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = ElegantText,
                        unfocusedTextColor = ElegantText,
                        focusedContainerColor = ElegantDarkBg,
                        unfocusedContainerColor = ElegantDarkBg,
                        focusedBorderColor = ElegantPrimary,
                        unfocusedBorderColor = ElegantOutline
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onDismiss, modifier = Modifier.heightIn(min = 48.dp)) {
                        Text("Cancelar", color = ElegantTextMuted)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (title.isNotBlank() && desc.isNotBlank()) {
                                onAddIdea(title, selectedCat, desc)
                            }
                        },
                        enabled = title.isNotBlank() && desc.isNotBlank(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ElegantPrimary,
                            contentColor = ElegantOnPrimary
                        ),
                        modifier = Modifier.testTag("submit_idea_button").heightIn(min = 48.dp)
                    ) {
                        Text("Publicar", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
