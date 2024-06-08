1
Simple  Text  Editor   source   code 



import javax. swing.*; 

import javax.swing.event.DocumentEvent; 

import javax.swing.event.DocumentListener; 

import javax.swing.text.*; 

import java. awt.*; 

import java.awt.event.ActionEvent; 

import java.awt.event.ActionListener; 

import java.awt.event.KeyEvent; 

import java.io.*; 



public class TextEditor extends JFrame implements DocumentListener { 

   private JTextPane textPane; 

   private JFileChooser file chooser; 

   private JComboBox<String> colorComboBox; 

   private JComboBox<Integer> sizeComboBox; 

   private JLabel status bar; 



   public TextEditor() { 

      super("Simple Text Editor"); 

      textPane = new JTextPane(); 

      fileChooser = new JFileChooser(); 

      statusBar = new JLabel("Line: 1, Column: 1"); 



      JScrollPane scrollPane = new JScrollPane(textPane); 

      add(scrollPane, BorderLayout.CENTER); 



      JMenuBar menuBar = new JMenuBar(); 

      setJMenuBar(menuBar); 

2
        // File menu 

      JMenu fileMenu = new JMenu("File"); 

      fileMenu.setMnemonic(KeyEvent.VK_F); 



      JMenuItem newMenuItem = new JMenuItem("New"); 

      JMenuItem openMenuItem = new JMenuItem("Open"); 

      JMenuItem saveMenuItem = new JMenuItem("Save"); 

      JMenuItem saveAsMenuItem = new JMenuItem("Save As"); 

      JMenuItem exitMenuItem = new JMenuItem("Exit"); 



      fileMenu.add(newMenuItem); 

      fileMenu.add(openMenuItem); 

      fileMenu.add(saveMenuItem); 

      fileMenu.add(saveAsMenuItem); 

      fileMenu.addSeparator(); 

      fileMenu.add(exitMenuItem); 



      menuBar.add(fileMenu); 



      //  Edit menu 

      JMenu editMenu = new JMenu("Edit"); 

      editMenu.setMnemonic(KeyEvent.VK_E); 



      JMenuItem undoMenuItem = new JMenuItem("Undo"); 

      JMenuItem cutMenuItem = new JMenuItem("Cut"); 

      JMenuItem copyMenuItem = new JMenuItem("Copy"); 

      JMenuItem pasteMenuItem = new JMenuItem("Paste"); 

      JMenuItem deleteMenuItem = new JMenuItem("Delete"); 



      editMenu.add(undoMenuItem); 

      editMenu.add(cutMenuItem); 

3
        editMenu.add(copyMenuItem); 

       editMenu.add(pasteMenuItem); 

       editMenu.add(deleteMenuItem); 



       menuBar.add(editMenu); 






       JMenu formatMenu  = new JMenu("Format"); 

       formatMenu.setMnemonic(KeyEvent.VK_O); 



       JMenuItem changeFontMenuItem = new JMenuItem("Change Font"); 

       formatMenu.add(changeFontMenuItem); 



       menuBar.add(formatMenu); 



       changeFontMenuItem.addActionListener(new  ActionListener() { 

          @Override 

          public void  actionPerformed(ActionEvent e) { 

              changeFont(); 

          } 

       }); 



       saveAsMenuItem.addActionListener(new ActionListener() { 

          @Override 

          public void  actionPerformed(ActionEvent e) { 

              int returnVal  = fileChooser.showSaveDialog(TextEditor.this); 



              if (returnVal == JFileChooser.APPROVE_OPTION) { 

                 File file = fileChooser.getSelectedFile(); 

                 writeFile(file); 

              } 

4
            } 

       }); 



       exitMenuItem.addActionListener(new  ActionListener()  { 

          @Override 

          public void  actionPerformed(ActionEvent e) { 

              System.exit(0); 

          } 

       }); 



       undoMenuItem.addActionListener(new  ActionListener()  { 

          @Override 

          public void  actionPerformed(ActionEvent e) { 

              //  Implement  Undo functionality 

              //  (You may need to use a DocumentUndoManager) 

          } 

       }); 



       cutMenuItem.addActionListener(new  ActionListener()  { 

          @Override 

          public void  actionPerformed(ActionEvent e) { 

              textPane.cut(); 

          } 

       }); 



       copyMenuItem.addActionListener(new  ActionListener() { 

          @Override 

          public void  actionPerformed(ActionEvent e) { 

              textPane.copy(); 

          } 

       }); 

5
 

       pasteMenuItem.addActionListener(new  ActionListener() { 

          @Override 

          public void  actionPerformed(ActionEvent e) { 

              textPane.paste(); 

          } 

       }); 



       deleteMenuItem.addActionListener(new  ActionListener()  { 

          @Override 

          public void  actionPerformed(ActionEvent e) { 

              textPane.replaceSelection(""); 

          } 

       }); 



       //  Text color and size options 

       JPanel optionsPanel = new JPanel(new FlowLayout()); 

       colorComboBox = new JComboBox<>(new String[]{"Black", "Red", "Blue", "Green"}); 

       sizeComboBox = new JComboBox<>(new Integer[]{12, 14, 16, 18, 20,24,28,32,40,60}); 



       colorComboBox.addActionListener(new ActionListener() { 

          @Override 

          public void  actionPerformed(ActionEvent e) { 

              changeTextColor((String) colorComboBox.getSelectedItem()); 

          } 

       }); 



       sizeComboBox.addActionListener(new ActionListener() { 

          @Override 

          public void  actionPerformed(ActionEvent e) { 

              changeTextSize((Integer) sizeComboBox.getSelectedItem()); 

6
            } 

       }); 



       optionsPanel.add(new JLabel("Text Color:")); 

       optionsPanel.add(colorComboBox); 

       optionsPanel.add(new JLabel("Text Size:")); 

       optionsPanel.add(sizeComboBox); 



       add(optionsPanel, BorderLayout.NORTH); 



       //  Status bar 

       add(statusBar, BorderLayout.SOUTH); 



       //  Add document listener to  the text area 

       textPane.getDocument().addDocumentListener(this); 

       textPane.addCaretListener(e -> updateStatusBar()); 



       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

       setSize(800, 600); 

       setLocationRelativeTo(null); 

       setVisible(true); 

   } 



   private void  changeFont() { 

       //  Get all available font names 

       GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment(); 

       String[] fontNames = ge.getAvailableFontFamilyNames(); 



       //  Display font chooser dialog 

       String selectedFontName = (String) JOptionPane.showInputDialog( 

              this, 

7
                "Choose Font", 

              "Font Chooser", 

              JOptionPane.PLAIN_MESSAGE, 

              null, 

              fontNames, 

              textPane.getFont().getFamily()); 



       //  Change the font if a font is selected 

       if (selectedFontName != null) { 

          Font selectedFont = new Font(selectedFontName, Font.PLAIN, textPane.getFont().getSize()); 

          textPane.setFont(selectedFont); 

       } 

   } 






   private void  updateStatusBar() { 

       try { 

          int  caretPosition = textPane.getCaretPosition(); 

          int  lineNumber = Utilities.getRowStart(textPane,  caretPosition) + 1; 

          int  columnNumber = caretPosition - Utilities.getRowStart(textPane,  caretPosition) + 1; 



          statusBar.setText("Line: " + lineNumber + ", Column: " + columnNumber); 

       } catch (BadLocationException e) { 

          e.printStackTrace(); 

       } 

   } 



   private void  readFile(File file) { 

       try (BufferedReader reader = new BufferedReader(new FileReader(file))) { 

          StringBuilder content  = new StringBuilder(); 

          String line; 

8
            while ((line = reader.readLine()) != null) { 

              content.append(line).append("\n"); 

          } 

          textPane.setText(content.toString()); 

       } catch (IOException ex) { 

          ex.printStackTrace(); 

       } 

   } 



   private void  writeFile(File file) { 

       try (BufferedWriter  writer  = new BufferedWriter(new  FileWriter(file)))  { 

          writer.write(textPane.getText()); 

       } catch (IOException ex) { 

          ex.printStackTrace(); 

       } 

   } 



   private void  changeTextColor(String colorName) { 

       StyledDocument doc = textPane.getStyledDocument(); 

       Style style = doc.addStyle("ColorStyle", null); 



       switch (colorName) { 

          case "Red": 

              StyleConstants.setForeground(style, Color.RED); 

              break; 

          case "Blue": 

              StyleConstants.setForeground(style, Color.BLUE); 

              break; 

          case "Green": 

              StyleConstants.setForeground(style, Color.GREEN); 

              break; 

9
            default: 

              StyleConstants.setForeground(style, Color.BLACK); 

              break; 

       } 



       textPane.setCharacterAttributes(style, false); 

   } 



   private void  changeTextSize(int size) { 

       StyledDocument doc = textPane.getStyledDocument(); 

       Style style = doc.addStyle("SizeStyle", null); 

       StyleConstants.setFontSize(style, size); 



       textPane.setCharacterAttributes(style, false); 

   } 



   //  Implement  DocumentListener methods 

   @Override 

   public void insertUpdate(DocumentEvent  e) { 

       updateTextStyles(); 

   } 



   @Override 

   public void removeUpdate(DocumentEvent  e) { 

       updateTextStyles(); 

   } 



   @Override 

   public void changedUpdate(DocumentEvent e) { 

       updateTextStyles(); 

   } 

10
 

   private void  updateTextStyles() { 

       //  You can customize this method based on your needs 

       //  For example, you may want to update text styles dynamically as the user types. 

   } 



   public static void main(String[]  args) { 

       SwingUtilities.invokeLater(() -> new TextEditor()); 

   } 

} 

